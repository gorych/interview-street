package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;

import java.util.List;

public interface UserInterviewDAO {

    List<UserInterview> getByInterviewId(int interviewId);

    UserInterview getByUserAndInterview(String username, String hash);

    void saveOrUpdate(UserInterview userInterview);

    void remove(UserInterview userInterview);

}
