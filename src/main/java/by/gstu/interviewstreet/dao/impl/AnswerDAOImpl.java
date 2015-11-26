package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IAnswerDAO;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Form;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerDAOImpl implements IAnswerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Answer insert(AnswerType type) {
        Answer answer = new Answer("New answer", type);
        sessionFactory.getCurrentSession().save(answer);
        return answer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Answer> getByIds(Integer[] ids) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Answer WHERE id IN (:ids)");
        query.setParameterList("ids", ids);
        return query.list();
    }

    @Override
    public void remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        Answer answer = (Answer) session.load(Answer.class, id);
        if (answer != null) {
            session.delete(answer);
        }
    }

    @Override
    public void remove(List<Form> forms) {
        Session session = sessionFactory.getCurrentSession();
        for (Form form : forms) {
            if (form != null) {
                session.delete(form.getAnswer());
            }
        }
    }
}
