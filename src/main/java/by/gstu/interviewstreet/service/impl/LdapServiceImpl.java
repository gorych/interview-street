package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.*;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.LdapService;
import by.gstu.interviewstreet.web.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

@Service
public class LdapServiceImpl implements LdapService {
    private static Logger LOG = LoggerFactory.getLogger(LdapServiceImpl.class);

    public static final String UNDEFINED_PASSWORD = "undefined";
    public static final String FIRST_NAME_ATTR = "givenName";
    public static final String SECOND_NAME_ATTR = "initials";
    public static final String LAST_NAME_ATTR = "sn";
    public static final String POST_NAME_ATTR = "title";
    public static final String SUBDIVISION_NAME = "ou";
    public static final String RECTOR_SUB = "ректорат";

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private SubdivisionDAO subdivisionDAO;

    private static String getAttr(String name, Attributes attr) {
        try {
            return "" + attr.get(name).get();
        } catch (NamingException e) {
            LOG.error("Default message from LDAP can't be interpreted to normal value. Attr name = " + name);
        }
        return "";
    }

    @Override
    @Transactional
    public User buildUser(Attributes attributes, String username) {
        String password = UNDEFINED_PASSWORD;

        String firstName = getAttr(FIRST_NAME_ATTR, attributes);
        String secondName = getAttr(SECOND_NAME_ATTR, attributes);
        String lastName = getAttr(LAST_NAME_ATTR, attributes);

        String postName = getAttr(POST_NAME_ATTR, attributes).toLowerCase();
        String subName = getAttr(SUBDIVISION_NAME, attributes).toLowerCase();

        Subdivision sub = subdivisionDAO.findByName(subName);
        Post post = postDAO.findByName(subName);

        if (sub == null) {
            sub = new Subdivision(subName);
            subdivisionDAO.add(sub);
        }

        if (post == null) {
            post = new Post(postName);
            postDAO.add(post);
        }

        Employee employee = employeeDAO.findByAll(firstName, secondName, lastName, sub, post);
        if (employee == null) {
            employee = new Employee(firstName, secondName, lastName, post, sub);
            employeeDAO.add(employee);
        }

        UserRole role;
        if (RECTOR_SUB.equals(subName)) {
            role = userRoleDAO.getByName(SecurityConstants.EDITOR);
        } else {
            role = userRoleDAO.getByName(SecurityConstants.RESPONDENT);
        }

        User user = new User(employee, role, username, password);
        userDAO.add(user);

        return user;
    }

}
