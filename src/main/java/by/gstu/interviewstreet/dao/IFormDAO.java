package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface IFormDAO {

    List<Form> getByQuestion(Question question);

    void insertForm(Form form);

    void saveForm(List<Answer> answers, Question question);

    void remove(Question question);

}
