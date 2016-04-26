package by.gstu.interviewstreet.web.controller.user;

import by.gstu.interviewstreet.bean.StatisticData;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.StatisticsService;
import by.gstu.interviewstreet.web.AttrConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DecimalFormat;
import java.util.List;

@Controller()
@RequestMapping("/viewer")
@Secured({UserRoleConstants.VIEWER, UserRoleConstants.EDITOR})
public class ViewerController {

    @Autowired
    InterviewService interviewService;

    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(value = {"/statistics"}, method = RequestMethod.GET)
    public String showStatistics(Model model) {
        new DecimalFormat();
        return "statistics";
    }

    @RequestMapping(value = {"/{hash}/statistics"}, method = RequestMethod.GET)
    public String showInterviewStatistics(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);

        int userAnswerCount = interview.getUserInterviews().size();
        int questionCount = interview.getQuestions().size();

        if (userAnswerCount < 1 || questionCount < 1) {
            model.addAttribute(AttrConstants.NOT_ANSWERS, true);
            return "statistics";
        }

        List<StatisticData> statistics = statisticsService.getInterviewStatistics(interview);

        model.addAttribute(AttrConstants.INTERVIEW_NAME, interview.getName());
        model.addAttribute(AttrConstants.STATISTICS, statistics);

        return "statistics";
    }

}

