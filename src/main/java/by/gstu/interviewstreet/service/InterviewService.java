package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;

import java.util.List;

public interface InterviewService {

    List<Interview> getAll();

    List<Interview> getByType(int typeId);

    List<Form> getQuestions(int interviewId);

    List<Form> getQuestions(long hash);

    List<List<Form>> getAnswers(List<Form> questionForm);

    String getJSON(Interview interview);

    String getLightJSON(List<Interview> interview);

    String getJson(int interviewId);

    Interview get(int interviewId);

    Interview get(long hash);

    Interview save(Interview interview);

    void remove(Interview interview);

    void lock(int id);
}
