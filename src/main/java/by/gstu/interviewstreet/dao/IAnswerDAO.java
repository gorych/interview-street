package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.UserAnswer;

import java.util.List;

public interface IAnswerDAO {

    Answer insert(AnswerType type);

    void insertUserAnswer(UserAnswer userAnswer);

    List<Answer> getByIds(List<Integer> ids);

    void remove(int id);

    void remove(List<Form> forms);

}
