package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface IQuestionDAO {

    Question insert(Question question);

    Question qetById(int id);

    List<Question> qet(List<Integer> ids);

    void remove(Question question);

}
