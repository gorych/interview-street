package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.UserDAO;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public UserDetails loadUserByUsername(String j_username) throws UsernameNotFoundException {
        String username = j_username.toUpperCase();

        User user = userDAO.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с такими учетными данными не найден.");
        }

        return user;
    }

    @Override
    @Transactional
    public User get(String username) {
        return userDAO.getByUsername(username);
    }
}
