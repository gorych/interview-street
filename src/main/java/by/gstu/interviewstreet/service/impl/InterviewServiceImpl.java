package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.impl.InterviewDAOImpl;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewDAOImpl interviewDAO;

    @Override
    @Transactional
    public List<Interview> getAllInterviews() {
        return interviewDAO.getAllInterviews();
    }

    @Override
    @Transactional
    public void insertInterview(Interview interview) {
        interviewDAO.insertInterview(interview);
    }

    @Override
    @Transactional
    public void insertInterview(Interview interview, List<User> users) {
        interviewDAO.insertInterview(interview, users);
    }
}
