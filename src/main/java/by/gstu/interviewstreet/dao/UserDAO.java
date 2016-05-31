package by.gstu.interviewstreet.dao;


import by.gstu.interviewstreet.domain.User;

import java.util.List;

public interface UserDAO extends GenericDAO<User, Integer> {

    User getByUsername(String passportData);

    List<User> getByPosts(Integer[] postIds, Integer[] subIds);

}
