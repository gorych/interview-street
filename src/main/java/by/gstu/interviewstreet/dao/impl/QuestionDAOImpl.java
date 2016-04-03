package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IQuestionDAO;
import by.gstu.interviewstreet.domain.Question;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class QuestionDAOImpl extends AbstractDbDAO implements IQuestionDAO {

    @Override
    public Question qetById(int id) {
        return (Question) getSession()
                .createQuery("FROM Question WHERE id = :id")
                .setInteger("id", id)
                .uniqueResult();
    }

    @Override
    public void insert(Question question) {
        getSession().save(question);
    }

    @Override
    public void remove(Question question) {
        if (question != null) {
            getSession().delete(question);
        }
    }

}
