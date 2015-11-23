package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;

import java.util.List;

public interface InterviewService {

    List<Interview> getAllInterviews();

    List<Form> getInterviewQuestions(int interviewId);

    List<List<Form>> getInterviewAnswers(List<Form> questionForm);

    String getJsonString(int interviewId);

    Interview getInterviewById(int interviewId);

    void insertInterview(Interview interview);

    void insertInterview(Interview interview, List<User> users);

    void removeInterviews(int[] ids);

    void hideInterview(int id);
}
