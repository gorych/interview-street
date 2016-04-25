package by.gstu.interviewstreet.web.controller.user;

import by.gstu.interviewstreet.bean.StatisticData;
import by.gstu.interviewstreet.dao.UserAnswerDAO;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.domain.UserAnswer;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DecimalFormat;
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
        new DecimalFormat();
        return "statistics";
    }

    @RequestMapping(value = {"/{hash}/statistics"}, method = RequestMethod.GET)
    public String showInterviewStatistics(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);
        List<Question> questions = interview.getSortedQuestions();

        List<StatisticData> statistics = new ArrayList<>();
        for (Question question : questions) {
            List<UserAnswer> allAnswers = question.getUserAnswers();
            List<UserAnswer> notDuplicateAnswers = userAnswerDAO.getAnswersByQuestion(question);

            int total = allAnswers.size();
            int maxEstimate = question.isRateType() ? Integer.parseInt(question.getAnswers().get(0).getText()) : 0;

            Map<Object, Object[]> answerData = new HashMap<>();
            for (UserAnswer userAnswer : notDuplicateAnswers) {
                int count = Collections.frequency(allAnswers, userAnswer);
                String percent = ControllerUtils.getPercent(count, total);
                answerData.put(userAnswer.getAnswerText(), new Object[]{count, percent});
            }

            statistics.add(new StatisticData(question, answerData, maxEstimate, total));
        }

        model.addAttribute(AttrConstants.INTERVIEW_NAME, interview.getName());
        model.addAttribute(AttrConstants.STATISTICS, statistics);

        return "statistics";
    }

}

