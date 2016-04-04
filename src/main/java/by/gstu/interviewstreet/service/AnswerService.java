package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.*;

import java.util.List;
import java.util.Map;

public interface AnswerService {

    Answer get(int id);

    List<Answer> get(List<Integer> ids);

    AnswerType getAnswerType(int id);

    void addDefaultAnswers(AnswerType type, Question question);

    Answer addDefaultTextAnswer(Question question);

    Answer addDefaultAnswer(AnswerType type, Question question);

    void remove(Answer answer);

}


