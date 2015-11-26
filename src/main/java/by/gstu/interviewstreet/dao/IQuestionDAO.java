package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Question;

public interface IQuestionDAO {

    long insertQuestion();

    Question qetQuestionById(int id);

    void remove(Question question);
}
