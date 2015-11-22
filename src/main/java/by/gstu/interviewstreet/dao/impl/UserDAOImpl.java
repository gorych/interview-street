package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IUserDAO;
import by.gstu.interviewstreet.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class UserDAOImpl implements IUserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUserByPassportData(String passportData) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User WHERE passportData = :passportData");
        query.setString("passportData", passportData);

        return (User) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsersByPosts(Collection postIds) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM User WHERE employee.post.id IN (:posts) " +
                "GROUP BY employee.id");
        query.setParameterList("posts", postIds);
        return query.list();
    }
}
