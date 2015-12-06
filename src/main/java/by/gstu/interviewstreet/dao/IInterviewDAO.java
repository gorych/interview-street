package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;

import java.util.List;


public interface IInterviewDAO {

    List<Interview> getAllInterviews();

    List<UserInterview> getUserInterviews(User user);

    List<Form> getInterviewQuestions(int interviewId);

    List<List<Form>> getInterviewAnswers(List<Form> questionForm);

    Interview getInterviewById(int id);

    int insertInterview(Interview interview);

    void insertInterview(Interview interview, List<User> users);

    void removeInterviews(List<Integer> interviewIds);

    void hideInterview(int interviewId);

    void passUserInterview(int interviewId);

}
