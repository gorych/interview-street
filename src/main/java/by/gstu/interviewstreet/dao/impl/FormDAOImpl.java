package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IFormDAO;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FormDAOImpl extends AbstractDbDAO implements IFormDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Form> getByQuestion(Question question) {
        return getSession()
                .createQuery("FROM Form WHERE question.id = :id")
                .setInteger("id", question.getId())
                .list();
    }

    @Override
    public void insertForm(Form form) {
        getSession().save(form);
    }

    @Override
    public void saveForm(List<Answer> answers, Question question) {
        Question oldQuestion = (Question) getSession().load(Question.class, question.getId());
        oldQuestion.setText(question.getText());

        for (Answer answer : answers) {
            Answer oldAnswer = (Answer) getSession().load(Answer.class, answer.getId());
            oldAnswer.setText(answer.getText());
            oldAnswer.setType(answer.getType());
        }
    }

    @Override
    public void remove(Question question) {
        getSession()
                .createQuery("DELETE FROM Form WHERE question.id=:id")
                .setInteger("id", question.getId())
                .executeUpdate();
    }
}
