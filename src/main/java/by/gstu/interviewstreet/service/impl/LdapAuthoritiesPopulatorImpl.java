package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.service.LdapService;
import by.gstu.interviewstreet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

import javax.naming.directory.Attributes;
import java.util.Collection;

@Component
public class LdapAuthoritiesPopulatorImpl implements LdapAuthoritiesPopulator {

    private static Logger LOG = LoggerFactory.getLogger(LdapAuthoritiesPopulatorImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LdapService ldapService;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(
            DirContextOperations dirContextOperations, String username) {

        User user = userService.get(username);
        if (user != null) {
            return user.getAuthorities();
        }

        Attributes attributes = dirContextOperations.getAttributes();
        user = ldapService.buildUser(attributes, username);

        LOG.info("User with username =" + username + " was created.");

        return user.getAuthorities();
    }

}
