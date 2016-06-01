package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class LdapAuthoritiesPopulatorImpl implements LdapAuthoritiesPopulator {

    @Autowired
    private UserService userService;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(
            DirContextOperations dirContextOperations, String username) {

        Logger LOG = LoggerFactory.getLogger(LdapAuthoritiesPopulatorImpl.class);

        User user = userService.get(username);
        if (user != null) {
            return user.getAuthorities();
        }

        Collection<GrantedAuthority> authorities = new HashSet<>();

        System.out.println("User with username =" + username + " not found.");
        LOG.info("User with username =" + username + " not found.");

        if ("МС2097146".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_EDITOR"));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_RESPONDENT"));

        return authorities;
    }

}
