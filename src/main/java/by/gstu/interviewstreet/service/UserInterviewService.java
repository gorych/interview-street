package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Interview;

public interface UserInterviewService {

    void addInterviewToUserByPost(Interview interview, Integer[] postIds, Integer[] subIds);

}
