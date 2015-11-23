package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IAnswerTypeDAO;
import by.gstu.interviewstreet.domain.AnswerType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AnswerTypeDAOImpl implements IAnswerTypeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public AnswerType getDefaultAnswerType() {
        final int DEFAULT_ID = 1;
        return getAnswerTypeById(DEFAULT_ID);
    }

    @Override
    public AnswerType getAnswerTypeById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM AnswerType WHERE id = :id");
        query.setInteger("id", id);

        return (AnswerType) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AnswerType> getAnswerTypesByIds(Integer[] ids) {
        List<AnswerType> answerTypes = new ArrayList<>();
        Query query = sessionFactory.getCurrentSession().createQuery("FROM AnswerType WHERE id = :id");
        for (Integer id : ids) {
            query.setInteger("id", id);
            answerTypes.add((AnswerType) query.uniqueResult());
        }

        return answerTypes;
    }
}
