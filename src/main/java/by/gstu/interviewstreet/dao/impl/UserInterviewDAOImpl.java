package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IUserInterviewDAO;
import by.gstu.interviewstreet.domain.UserInterview;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInterviewDAOImpl implements IUserInterviewDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<UserInterview> getUserInterviewsById(int interviewId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM UserInterview WHERE interview.id =:interviewId " +
                "GROUP BY user.employee.post.id");
        query.setInteger("interviewId", interviewId);
        return query.list();
    }
}
