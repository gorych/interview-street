package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface QuestionService {

    long insert();

    Question get(int id);

    List<Question> qet(List<Integer> ids);

    String getJSON(List<Form> questionForms);
}
