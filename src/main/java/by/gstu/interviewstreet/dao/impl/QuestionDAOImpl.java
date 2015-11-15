package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IQuestionDAO;
import by.gstu.interviewstreet.domain.Question;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class QuestionDAOImpl implements IQuestionDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public long insertQuestion() {
        Question question = new Question("New question");
        Serializable result = sessionFactory.getCurrentSession().save(question);
        if (result != null) {
            return (Integer) result;
        }
        return -1;
    }
}
