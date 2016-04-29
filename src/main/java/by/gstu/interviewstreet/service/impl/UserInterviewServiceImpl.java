package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.UserDAO;
import by.gstu.interviewstreet.dao.UserInterviewDAO;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.service.SortType;
import by.gstu.interviewstreet.service.UserInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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

    @Override
    public List<UserInterview> sortByEmployeeLastname(List<UserInterview> userInterviews, SortType sortType) {
        Collections.sort(userInterviews, (first, second) -> {
            String lastname1 = first.getUser().getEmployee().getLastname();
            String lastname2 = second.getUser().getEmployee().getLastname();

            if (sortType == SortType.ASC) {
                return lastname1.compareTo(lastname2);
            }

            if (sortType == SortType.DESC) {
                return lastname2.compareTo(lastname1);
            }

            return 0;
        });

        return userInterviews;
    }

    @Override
    public List<UserInterview> sortByDate(List<UserInterview> userInterviews, SortType sortType) {
        return null;
    }

    @Override
    public List<UserInterview> sortByStatus(List<UserInterview> userInterviews, SortType sortType) {
        Collections.sort(userInterviews, (first, second) -> {
            Boolean val1 = first.getPassed();
            Boolean val2 = second.getPassed();

            if (sortType == SortType.ASC) {
                return val1.compareTo(val2);
            }

            if (sortType == SortType.DESC) {
                return val2.compareTo(val1);
            }

            return 0;
        });

        return userInterviews;
    }

}
