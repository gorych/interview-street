package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.*;

import java.util.List;

public interface FormService {

    List<Form> buildQuestionForm(Interview interview, AnswerType answerType);

    void save(List<Answer> answers, Question question);

    void remove(Question question);

    String getJSON(int questionId);

}
