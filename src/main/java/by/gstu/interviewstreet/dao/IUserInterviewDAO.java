package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.UserInterview;

import java.util.List;

public interface IUserInterviewDAO {

    List<UserInterview> getById(int interviewId);

}
