package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.UserInterview;

public interface UserInterviewService {

    void addInterviewToUserByPost(Interview interview, Integer[] postIds, Integer[] subIds);

    UserInterview getByUserAndInterview(String username, String hash);

}
