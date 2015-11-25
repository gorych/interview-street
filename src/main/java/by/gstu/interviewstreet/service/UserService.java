package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    User get(String passportData);

    List<User> getUsers(Collection postIds);

}
