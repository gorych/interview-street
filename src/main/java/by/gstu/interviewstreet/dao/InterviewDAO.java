package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.ExpertInterview;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.PublishedInterview;

import java.util.List;


public interface InterviewDAO extends GenericDAO<Interview, Integer> {

    List<PublishedInterview> getPublishedInterviews(Interview interview);

    List<Interview> getAllInRange(int from, int howMany, String userCredential);

    PublishedInterview getPublishedById(int id);

    Interview getByHash(String hash);

    void saveExpertInterview(ExpertInterview expertInterview);

    void lockOrUnlock(int interviewId);

}
