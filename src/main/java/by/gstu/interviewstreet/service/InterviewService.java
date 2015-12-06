package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.ExtendUserInterview;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.web.param.RequestParamException;

import java.util.List;

public interface InterviewService {

    List<Interview> getAll();

    List<Form> getQuestions(int interviewId);

    List<List<Form>> getAnswers(List<Form> questionForm);

    String getJSON(Interview interview);

    Interview get(int interviewId);

    Interview insert(ExtendUserInterview userInterview) throws RequestParamException;

    void remove(List<Integer> ids);

    void hide(int id);
}
