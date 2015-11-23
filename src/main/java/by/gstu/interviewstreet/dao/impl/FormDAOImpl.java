package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IFormDAO;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FormDAOImpl implements IFormDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void insertForm(Form form) {
        sessionFactory.getCurrentSession().save(form);
    }

    @Override
    public void saveForm(List<Answer> answers, Question question) {
        Session session = sessionFactory.getCurrentSession();

        Question oldQuestion = (Question) session.load(Question.class, question.getId());
        oldQuestion.setText(question.getText());

        for (Answer answer : answers) {
            Answer oldAnswer = (Answer) session.load(Answer.class, answer.getId());
            oldAnswer.setText(answer.getText());
            oldAnswer.setType(answer.getType());
        }
    }
}
