package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface FormService {

    void save(List<Answer> answers, Question question);

    void remove(Question question);

    public String getJsonString(int questionId);

}
