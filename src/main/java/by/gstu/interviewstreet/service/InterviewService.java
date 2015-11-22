package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;

import java.util.List;

public interface InterviewService {

    List<Interview> getAllInterviews();

    void insertInterview(Interview interview);

    void insertInterview(Interview interview, List<User> users);

}
