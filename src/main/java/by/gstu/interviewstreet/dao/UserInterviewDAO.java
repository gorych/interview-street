package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.UserInterview;

import java.util.List;

public interface UserInterviewDAO extends GenericDAO<UserInterview, Integer> {

    List<UserInterview> getByInterviewAndGroupByPost(int interviewId);

    List<UserInterview> getByInterviewAndGroupBySubdivision(String hash);

    List<UserInterview> getByInterviewHash(String hash);

    UserInterview getByUserAndInterview(String username, String hash);

}
