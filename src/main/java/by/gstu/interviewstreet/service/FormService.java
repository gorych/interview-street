package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface FormService {

    void saveForm(List<Answer> answers, Question question);

}
