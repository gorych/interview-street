package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.SubdivisionDAO;
import by.gstu.interviewstreet.dao.UserInterviewDAO;
import by.gstu.interviewstreet.domain.Subdivision;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.service.SubdivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubdivisionServiceImpl implements SubdivisionService {

    @Autowired
    SubdivisionDAO subdivisionDAO;

    @Autowired
    UserInterviewDAO userInterviewDAO;

    @Override
    @Transactional
    public List<Subdivision> getAll() {
        return subdivisionDAO.getAll();
    }

    @Override
    @Transactional
    public List<Subdivision> getSubdivisionsByInterview(String hash) {
        List<UserInterview> userInterviews = userInterviewDAO.getByInterviewAndGroupBySubdivision(hash);
        return userInterviews.stream()
                .map(uInterview -> uInterview.getUser().getEmployee().getSubdivision())
                .collect(Collectors.toList());
    }
}
