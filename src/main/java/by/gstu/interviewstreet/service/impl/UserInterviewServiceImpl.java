package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.UserDAO;
import by.gstu.interviewstreet.dao.UserInterviewDAO;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.service.UserInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInterviewServiceImpl implements UserInterviewService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserInterviewDAO userInterviewDAO;

    @Override
    @Transactional
    public void addInterviewToUserByPost(Interview interview, Integer[] postIds, Integer[] subIds) {
        List<User> users = userDAO.getByPosts(postIds, subIds);
        UserInterview userInterview;
        for (User user : users) {
            userInterview = new UserInterview(interview, user);
            userInterviewDAO.save(userInterview);
        }
    }

}
