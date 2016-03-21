package by.gstu.interviewstreet.dao;


import by.gstu.interviewstreet.domain.User;

import java.util.Collection;
import java.util.List;

public interface IUserDAO {

    User getByPassportData(String passportData);

    List<User> getByPosts(Integer[] postIds);

}
