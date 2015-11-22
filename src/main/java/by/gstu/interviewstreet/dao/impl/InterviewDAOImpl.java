package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IInterviewDAO;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class InterviewDAOImpl implements IInterviewDAO{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Interview> getAllInterviews() {
        return sessionFactory.getCurrentSession().createQuery("from Interview")
                .list();
    }

    @Override
    public int insertInterview(Interview interview) {
        Serializable result = sessionFactory.getCurrentSession().save(interview);
        if (result != null) {
            return (Integer) result;
        }
        return -1;
    }

    @Override
    public void insertInterview(Interview interview, List<User> users) {
        int id = insertInterview(interview);
        interview.setId(id);

        Session session = sessionFactory.getCurrentSession();
        for (User user : users){
            session.save(new UserInterview(interview, user));
        }
    }
}
