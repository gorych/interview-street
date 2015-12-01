package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IQuestionDAO;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class QuestionDAOImpl implements IQuestionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public long insertQuestion() {
        Question question = new Question("Новый вопрос");
        Serializable result = sessionFactory.getCurrentSession().save(question);
        if (result != null) {
            return (Integer) result;
        }
        return -1;
    }

    @Override
    public Question qetQuestionById(int id) {
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query query = session.createQuery("FROM Question WHERE id = :id");
        query.setInteger("id", id);

        return (Question) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Question> qetQuestions(List<Integer> ids) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Question WHERE id IN (:ids) ");
        query.setParameterList("ids", ids);
        return query.list();
    }

    @Override
    public void remove(Question question) {
        Session session = sessionFactory.getCurrentSession();
        if (question != null) {
            session.delete(question);
        }
    }

    @Override
    public Number count(Interview interview) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT COUNT (*) FROM Form WHERE interview.id = :id GROUP BY question.id");
        query.setInteger("id", interview.getId());
        return (Number) query.uniqueResult();
    }
}
