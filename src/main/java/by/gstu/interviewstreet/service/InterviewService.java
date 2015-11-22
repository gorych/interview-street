package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;

import java.util.List;

public interface InterviewService {

    List<Interview> getAllInterviews();

    String getJsonString(int interviewId);

    void insertInterview(Interview interview);

    void insertInterview(Interview interview, List<User> users);

    void removeInterviews(int[] ids);

    void hideInterview(int id);
}
