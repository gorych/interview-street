package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface QuestionService {

    Question get(int id);

    String getJSON(List<Form> questionForms);
}
