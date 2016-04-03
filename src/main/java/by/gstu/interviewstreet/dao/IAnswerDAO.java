package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.UserAnswer;

import java.util.List;

public interface IAnswerDAO {

    List<Answer> getByIds(List<Integer> ids);

    void saveOrUpdate(Answer answer);

    void remove(int id);

}
