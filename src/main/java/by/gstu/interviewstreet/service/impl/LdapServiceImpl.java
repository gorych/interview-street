package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.*;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.LdapService;
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
        String password = "undefined";

        String firstName = getAttr("givenName", attributes);
        String secondName = getAttr("initials", attributes);
        String lastName = getAttr("sn", attributes);

        String postName = getAttr("title", attributes);
        String subName = getAttr("ou", attributes);

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
        if ("Ректорат".equals(subName)) {
            role = userRoleDAO.getByName("ROLE_EDITOR");
        } else {
            role = userRoleDAO.getByName("ROLE_RESPONDENT");
        }

        User user = new User(employee, role, username, password);
        userDAO.add(user);

        return user;
    }

}
