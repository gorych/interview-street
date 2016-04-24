package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.domain.UserAnswer;

import java.util.List;

public interface UserAnswerDAO {

    void saveOrUpdate(UserAnswer userAnswer);

    List<UserAnswer> getAnswersByQuestion(Question question);

}
