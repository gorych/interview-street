package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IAnswerDAO;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class AnswerDAOImpl implements IAnswerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public long insertAnswer(AnswerType type) {
        Answer answer = new Answer("New answer", type);
        Serializable result = sessionFactory.getCurrentSession().save(answer);
        if (result != null) {
            return (Integer) result;
        }
        return -1;
    }
}
