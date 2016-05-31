package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.InterviewDAO;
import by.gstu.interviewstreet.dao.UserDAO;
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

import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public UserDetails loadUserByUsername(String j_username) throws UsernameNotFoundException {
        String username = j_username.toUpperCase();
        by.gstu.interviewstreet.domain.User user = userDAO.getByPassportData(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с такими паспортными данными не найден.");
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
    public by.gstu.interviewstreet.domain.User get(String passportData) {
        return userDAO.getByPassportData(passportData.toUpperCase());
    }
}
