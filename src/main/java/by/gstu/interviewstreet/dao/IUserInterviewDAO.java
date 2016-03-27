package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.UserInterview;

import java.util.List;

public interface IUserInterviewDAO {

    List<UserInterview> getByInterviewId(int interviewId);

    void save(UserInterview userInterview);

    void remove(UserInterview userInterview);

}
