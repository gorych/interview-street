package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface QuestionService {

    Question get(int id);

    List<Question> getAllOrderByNumber(String hash);

    Question add(Interview interview, int number);

    void move(int questId, int number);

    void remove(Question question);

}
