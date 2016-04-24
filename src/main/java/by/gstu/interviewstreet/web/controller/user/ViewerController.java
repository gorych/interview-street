package by.gstu.interviewstreet.web.controller.user;

import by.gstu.interviewstreet.bean.StatisticData;
import by.gstu.interviewstreet.bean.StatisticDataKey;
import by.gstu.interviewstreet.dao.UserAnswerDAO;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.domain.UserAnswer;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.web.AttrConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller()
@RequestMapping("/viewer")
@Secured({UserRoleConstants.VIEWER, UserRoleConstants.EDITOR})
public class ViewerController {

    @Autowired
    InterviewService interviewService;

    @Autowired
    UserAnswerDAO userAnswerDAO;

    @RequestMapping(value = {"/statistics"}, method = RequestMethod.GET)
    public String showStatistics(Model model) {

        return "statistics";
    }

    @RequestMapping(value = {"/{hash}/statistics"}, method = RequestMethod.GET)
    public String showInterviewStatistics(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);

        List<Question> questions = interview.getQuestions();

        Map<StatisticDataKey, List<StatisticData>> statistics = new TreeMap<>();
        for (Question question : questions) {
            List<UserAnswer> allAnswers = question.getUserAnswers();
            List<UserAnswer> notDuplicateAnswers = userAnswerDAO.getAnswersByQuestion(question);

            int total = allAnswers.size();

            List<StatisticData> data = new ArrayList<>();
            for (UserAnswer userAnswer : notDuplicateAnswers) {
                int count = Collections.frequency(allAnswers, userAnswer);
                data.add(new StatisticData(count, total, userAnswer.getAnswerText()));
            }

            StatisticDataKey key = new StatisticDataKey(question.getText(), question.getType().getName());
            statistics.put(key, data);
        }

        model.addAttribute(AttrConstants.INTERVIEW_NAME, interview.getName());
        model.addAttribute(AttrConstants.STATISTICS, statistics);

        return "statistics";
    }

}

