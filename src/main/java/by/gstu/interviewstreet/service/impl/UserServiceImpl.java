package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IUserDAO;
import by.gstu.interviewstreet.security.UserPosition;
import by.gstu.interviewstreet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    IUserDAO userDAO;

    @Transactional
    public UserDetails loadUserByUsername(String j_username) throws UsernameNotFoundException {
        String username = j_username.toUpperCase();
        by.gstu.interviewstreet.domain.User user= userDAO.getUserByPassportData(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        int roleIndex = user.getRole().getId();
        String[] roles = UserPosition.values()[roleIndex].getRole();

        Set<SimpleGrantedAuthority> userRoles = new HashSet<>();
        for (String role : roles) {
            userRoles.add(new SimpleGrantedAuthority(role));
        }

        return new User(username, username, userRoles);
    }

    @Override
    @Transactional
    public by.gstu.interviewstreet.domain.User getUserByPassportData(String passportData) {
        return userDAO.getUserByPassportData(passportData.toUpperCase());
    }

    @Override
    @Transactional
    public List<by.gstu.interviewstreet.domain.User> getUsersByPosts(Collection postIds) {
        return userDAO.getUsersByPosts(postIds);
    }
}
