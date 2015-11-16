package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IAnswerTypeDAO;
import by.gstu.interviewstreet.domain.AnswerType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerTypeDAOImpl implements IAnswerTypeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public AnswerType getDefaultAnswerType() {
        final int DEFAULT_ID = 1;

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM AnswerType WHERE id = :id");
        query.setInteger("id", DEFAULT_ID);

        return (AnswerType) query.uniqueResult();
    }
}
