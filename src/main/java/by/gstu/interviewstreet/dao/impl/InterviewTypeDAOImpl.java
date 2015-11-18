package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
import by.gstu.interviewstreet.domain.InterviewType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InterviewTypeDAOImpl implements IInterviewTypeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public InterviewType getTypeById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM InterviewType WHERE id = :id");
        query.setInteger("id", id);

        return (InterviewType) query.uniqueResult();
    }
}
