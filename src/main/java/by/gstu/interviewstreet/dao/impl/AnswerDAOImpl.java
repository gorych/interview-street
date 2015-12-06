package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IAnswerDAO;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.UserAnswer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerDAOImpl extends AbstractDbDAO implements IAnswerDAO {


    @Override
    public Answer insert(AnswerType type) {
        Answer answer = new Answer("Новый ответ", type);
        getSession().save(answer);
        return answer;
    }

    @Override
    public void insertUserAnswer(UserAnswer userAnswer) {
        getSession().save(userAnswer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Answer> getByIds(List<Integer> ids) {
        return getSession()
                .createQuery("FROM Answer WHERE id IN (:ids)")
                .setParameterList("ids", ids)
                .list();
    }

    @Override
    public void remove(int id) {
        Answer answer = (Answer) getSession().load(Answer.class, id);
        if (answer != null) {
            getSession().delete(answer);
        }
    }

    @Override
    public void remove(List<Form> forms) {
        for (Form form : forms) {
            if (form != null) {
                getSession().delete(form.getAnswer());
            }
        }
    }
}
