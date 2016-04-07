package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.UserDAO;
import by.gstu.interviewstreet.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl extends AbstractDbDAO implements UserDAO {

    @Override
    public User getByPassportData(String passportData) {
        return (User) getSession()
                .createQuery("FROM User WHERE passportData LIKE :passportData")
                .setString("passportData", passportData)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getByPosts(Integer[] postIds) {
        return getSession()
                .createQuery("FROM User WHERE employee.post.id IN (:posts) GROUP BY employee.id")
                .setParameterList("posts", postIds)
                .list();
    }
}
