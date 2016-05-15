package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.ExpertInterview;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.PublishedInterview;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

public interface InterviewService {

    Interview get(int interviewId);

    Interview get(String hash);

    PublishedInterview getPublish(Integer id);

    List<Interview> getAll();

    List<PublishedInterview> getPublishedInterviews(Interview interview);

    List<Interview> getAllInRangeByUser(int from, int howMany, String userCredential);

    Map<String, Object> getModelMapForEditForm(int interviewId);

    Interview saveOrUpdate(Interview interview);

    void saveExpertInterview(ExpertInterview expertInterview);

    void update(Interview interview);

    void lockOrUnlock(int id);

    void remove(Interview interview);

    void hideExpiredInterviews();
}
