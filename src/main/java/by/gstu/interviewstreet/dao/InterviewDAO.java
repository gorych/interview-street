package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.ExpertInterview;
import by.gstu.interviewstreet.domain.Interview;

import java.util.List;


public interface InterviewDAO {

    List<Interview> getAll();

    List<Interview> getAllInRange(int from, int howMany, String userCredential);

    Interview getById(int id);

    Interview getByHash(String hash);

    void saveOrUpdate(Interview interview);

    void saveExpertInterview(ExpertInterview expertInterview);

    void remove(Interview interview);

    void lockOrUnlock(int interviewId);

}
