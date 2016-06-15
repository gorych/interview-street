package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.domain.UserAnswer;

import java.util.List;

public interface UserAnswerDAO extends GenericDAO<UserAnswer, Integer> {

    List<UserAnswer> getAnswersByQuestion(Question question);

    List<UserAnswer> getAnswersByInterviewHashAndText(Interview interview, String text);

}
