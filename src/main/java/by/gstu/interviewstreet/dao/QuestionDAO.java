package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface QuestionDAO extends GenericDAO<Question, Integer> {

    Question getByNumber(int number);

    List<Question> getAllWhoseNumberMoreOrEquals(int number);

    List<Question> getAllWhoseNumberMore(int number);

    List<Question> getAllOrderByNumber(String hash);

    void incrementNumbers(List<Question> questions);

    void decrementNumbers(List<Question> questions);

}
