package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Form;

import java.util.List;

public interface AnswerService {

    long insert(Form form);

    List<Answer> get(Integer[] ids);

    List<AnswerType> getAnswerTypes(Integer[] ids);

    void remove(int id);

}


