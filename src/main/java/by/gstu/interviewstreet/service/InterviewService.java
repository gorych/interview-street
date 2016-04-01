package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Interview;

import java.util.List;
import java.util.Map;

public interface InterviewService {

    List<Interview> getAll();

    Map<String, Object> getValueMapForCard(int interviewId);

    Interview get(int interviewId);

    Interview get(long hash);

    Interview saveOrUpdate(Interview interview);

    void remove(Interview interview);

    void lockOrUnlock(int id);
}
