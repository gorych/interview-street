package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;

import java.util.List;


public interface IInterviewDAO {

    List<Interview> getAllInterviews();

    int insertInterview(Interview interview);

    void insertInterview(Interview interview, List<User> users);

}
