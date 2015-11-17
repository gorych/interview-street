package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IInterviewDAO;
import by.gstu.interviewstreet.domain.Interview;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InterviewDAOImpl implements IInterviewDAO{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void insertInterview(Interview interview) {
        sessionFactory.getCurrentSession().save(interview);
    }
}
