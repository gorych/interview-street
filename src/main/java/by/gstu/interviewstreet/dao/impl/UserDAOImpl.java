package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.UserDAO;
import by.gstu.interviewstreet.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Integer> implements UserDAO {

    @Override
    public User getByUsername(String username) {
        return (User) currentSession()
                .createQuery("FROM User WHERE username LIKE :username")
                .setString("username", username)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getByPosts(Integer[] postIds, Integer[] subIds) {
        return currentSession()
                .createQuery("FROM User WHERE employee.subdivision.id IN (:subs) AND employee.post.id IN (:posts) GROUP BY employee.id")
                .setParameterList("subs", subIds)
                .setParameterList("posts", postIds)
                .list();
    }
}
