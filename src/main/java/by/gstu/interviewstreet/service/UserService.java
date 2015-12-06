package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;

import java.util.Collection;
import java.util.List;

public interface UserService {

    User get(String passportData);

    List<User> getUsers(Collection postIds);

    List<UserInterview> getInterviews(String username);
}
