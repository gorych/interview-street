package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    Question get(int id);

    List<Question> getAllOrderByNumber(String hash);

    Question addQuestion(Interview interview, int number);

    void move(int questId, int number);

    Map<String, Object> getValueMapForQuestionForm(Question question, AnswerType answerType);

    Map<String, Object> getValueMapForDuplicateQuestionForm(Question question, Question duplicate, AnswerType answerType);

    void remove(Question question);

}
