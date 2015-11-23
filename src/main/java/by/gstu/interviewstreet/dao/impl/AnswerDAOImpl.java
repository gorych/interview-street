package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IAnswerDAO;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerDAOImpl implements IAnswerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Answer insertAnswer(AnswerType type) {
        Answer answer = new Answer("New answer", type);
        sessionFactory.getCurrentSession().save(answer);
        return answer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Answer> getAnswersByIds(Integer[] ids) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Answer WHERE id IN (:ids)");
        query.setParameterList("ids", ids);
        return query.list();
    }
}
