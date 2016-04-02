package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface IQuestionDAO {

    Question qetById(int id);

    void remove(Question question);

}
