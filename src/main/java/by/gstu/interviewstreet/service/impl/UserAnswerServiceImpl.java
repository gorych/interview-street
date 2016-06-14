package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.UserAnswerDAO;
import by.gstu.interviewstreet.dao.UserInterviewDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.UserAnswerService;
import by.gstu.interviewstreet.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {

    private static final String RESPONDENT_LIST_ROW_PATTERN = "%d. %s (%s)";

    @Autowired
    UserAnswerDAO userAnswerDAO;

    @Autowired
    UserInterviewDAO userInterviewDAO;

    @Override
    @Transactional
    public void save(User user, Interview interview, List<Answer> answers) {
        List<Question> existQuestions = interview.getQuestions();

        UserInterview userInterview = null;
        if (user != null) {
            userInterview = userInterviewDAO.getByUserAndInterview(user.getUsername(), interview.getHash());

            if (!interview.isSecondPassage() && userInterview.getPassed()) {
                throw new IllegalArgumentException("User already passed this interview. Hash is " + interview.getHash());
            }
        }

        for (Answer answer : answers) {
            Question question = answer.getQuestion();

            int index = existQuestions.indexOf(question);
            if (index < 0) {
                continue;
            }

            question = existQuestions.get(index);
            List<Answer> existAnswers = question.getAnswers();

            if (existAnswers.contains(answer)) {
                userAnswerDAO.saveOrUpdate(new UserAnswer(user, question, interview, answer, answer.getText(), DateUtils.getToday()));

                if (userInterview != null) {
                    userInterview.setPassed(true);
                    userInterview.setPassingDate(DateUtils.getToday());
                    userInterviewDAO.add(userInterview);
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getRespondentList(String hash, String text) {
        List<UserAnswer> userAnswers = userAnswerDAO.getAnswersByInterviewHashAndText(hash, text);
        ArrayList<String> respondentList = new ArrayList<>();

        for (int i = 0; i < userAnswers.size(); i++) {
            UserAnswer userAnswer = userAnswers.get(i);

            User user = userAnswer.getUser();
            if (user == null) {
                continue;
            }
            Employee employee = user.getEmployee();
            respondentList.add(String.format(RESPONDENT_LIST_ROW_PATTERN, (i + 1), employee.getFullName(), employee.getSubdivision().getName()));
        }

        return respondentList;
    }
}
