package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.UserAnswer;

import java.util.List;

public interface AnswerDAO extends GenericDAO<Answer, Integer> {

    List<Answer> getByIds(List<Integer> ids);

}
