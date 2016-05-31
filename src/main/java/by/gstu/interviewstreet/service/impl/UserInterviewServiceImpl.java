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
        List<User> users;
        if (subIds == null || subIds.length < 1 || postIds == null || postIds.length < 1) {
            users = userDAO.getAll();
        } else {
            users = userDAO.getByPosts(postIds, subIds);
        }

        UserInterview userInterview;
        for (User user : users) {
            userInterview = new UserInterview(interview, user);
            userInterviewDAO.add(userInterview);
        }
    }

    @Override
    @Transactional
    public UserInterview getByUserAndInterview(String username, String hash) {
        return userInterviewDAO.getByUserAndInterview(username, hash);
    }

    @Override
    @Transactional
    public List<UserInterview> getByInterviewHash(String hash) {
        return userInterviewDAO.getByInterviewHash(hash);
    }

}
