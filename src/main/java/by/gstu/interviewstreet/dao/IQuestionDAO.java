package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface IQuestionDAO {

    long insertQuestion();

    Question qetQuestionById(int id);

    List<Question> qetQuestions(List<Integer> ids);

    void remove(Question question);

    Number count(Interview interview);
}
