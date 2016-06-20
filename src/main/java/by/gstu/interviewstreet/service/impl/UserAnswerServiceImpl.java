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
    private static final String DEFAULT_SUBDIVISION = "Отдел не определен";
    private static final String DEFAULT_NAME = "Аноним";

    @Autowired
    UserAnswerDAO userAnswerDAO;

    @Autowired
    UserInterviewDAO userInterviewDAO;

    @Override
    @Transactional
    public void save(ExpertInterview expert, User user, Interview interview, List<Answer> answers) {
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
                userAnswerDAO.saveOrUpdate(new UserAnswer(user, expert, question, interview, answer, answer.getText(), DateUtils.getToday()));

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
    public List<String> getRespondentList(Interview interview, String text) {
        List<UserAnswer> userAnswers = userAnswerDAO.getAnswersByInterviewHashAndText(interview, text);
        ArrayList<String> respondentList = new ArrayList<>();

        for (int i = 0; i < userAnswers.size(); i++) {
            UserAnswer userAnswer = userAnswers.get(i);

            User user = userAnswer.getUser();
            String fullName = DEFAULT_NAME;
            String subdivision = DEFAULT_SUBDIVISION;

            if (user == null && interview.isExpertType()) {
                fullName = userAnswer.getExpert().getFullName();
            } else if (user != null) {
                Employee employee = user.getEmployee();
                fullName = employee.getFullName();
                subdivision = employee.getSubdivision().getName();
            }

            respondentList.add(String.format(RESPONDENT_LIST_ROW_PATTERN, (i + 1), fullName, subdivision));
        }
        return respondentList;
    }

    @Override
    @Transactional
    public String getRespondentListHowLine(Interview interview, String text) {
        List<UserAnswer> userAnswers = userAnswerDAO.getAnswersByInterviewHashAndText(interview, text);
        StringBuilder respondentList = new StringBuilder();

        for (int i = 0; i < userAnswers.size(); i++) {
            UserAnswer userAnswer = userAnswers.get(i);

            RespondentData respondentData = new RespondentData(interview, userAnswer).invoke();
            String fullName = respondentData.getFullName();
            String subdivision = respondentData.getSubdivision();

            String returnCarriage = Character.toString((char)13) + Character.toString((char)10);
            respondentList
                    .append(String.format(RESPONDENT_LIST_ROW_PATTERN, (i + 1), fullName, subdivision))
                    .append(returnCarriage);
        }
        return respondentList.toString();
    }

    private class RespondentData {
        private Interview interview;
        private UserAnswer userAnswer;
        private String fullName;
        private String subdivision;

        public RespondentData(Interview interview, UserAnswer userAnswer) {
            this.interview = interview;
            this.userAnswer = userAnswer;
        }

        public String getFullName() {
            return fullName;
        }

        public String getSubdivision() {
            return subdivision;
        }

        public RespondentData invoke() {
            User user = userAnswer.getUser();
            fullName = DEFAULT_NAME;
            subdivision = DEFAULT_SUBDIVISION;

            if (user == null && interview.isExpertType()) {
                fullName = userAnswer.getExpert().getFullName();
            } else if (user != null) {
                Employee employee = user.getEmployee();
                fullName = employee.getFullName();
                subdivision = employee.getSubdivision().getName();
            }
            return this;
        }
    }
}
