package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;
import com.google.gson.JsonArray;

import java.util.List;
import java.util.Map;

public interface InterviewService {

    List<Interview> getAll();

    List<Interview> getByType(int typeId);

    List<Form> getQuestions(int interviewId);

    List<Form> getQuestions(long hash);

    List<List<Form>> getAnswers(List<Form> questionForm);

    String getLightJSON(List<Interview> interview);

    Map<String, Object> getValueMapForCard(int interviewId);

    Interview get(int interviewId);

    Interview get(long hash);

    Interview save(Interview interview);

    void remove(Interview interview);

    void lockOrUnlock(int id);
}
