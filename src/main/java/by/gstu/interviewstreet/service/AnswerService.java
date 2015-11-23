package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Form;

import java.util.List;

public interface AnswerService {

    long insertAnswer(Form form);

    List<Answer> getAnswersByIds(Integer[] ids);

    public List<AnswerType> getAnswerTypesByIds(Integer[] ids);

}


