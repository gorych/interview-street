package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.UserInterview;

import java.util.List;

public interface UserInterviewService {

    void addInterviewToUserByPost(Interview interview, Integer[] postIds, Integer[] subIds);

    UserInterview getByUserAndInterview(String username, String hash);

    List<UserInterview> getByInterviewHash(String hash);

    List<UserInterview> sortByEmployeeLastname(List<UserInterview> userInterviews, SortType sortType);

    List<UserInterview> sortByDate(List<UserInterview> userInterviews, SortType sortType);

    List<UserInterview> sortByStatus(List<UserInterview> userInterviews, SortType sortType);

}
