package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface AnswerService {

   Answer get(int id);

    Answer get(Question question, int id);

    List<Answer> get(List<Integer> ids);

  void saveOrUpdate(Answer answer);

    List<Answer> addDefaultAnswers(Question question);

    List<Answer> duplicateAnswers(Question question, Question duplicated);

    Answer addDefaultTextAnswer(Question question);

    Answer addDefaultAnswer(AnswerType type, Question question);

  void remove(Answer answer);

}


