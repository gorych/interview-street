package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;

import java.util.List;

public interface IAnswerDAO {

    Answer insertAnswer(AnswerType type);

    List<Answer> getAnswersByIds(Integer[] ids);

}
