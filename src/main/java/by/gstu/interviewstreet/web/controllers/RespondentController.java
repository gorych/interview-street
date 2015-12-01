package by.gstu.interviewstreet.web.controllers;

import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.AnswerService;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.UserService;
import by.gstu.interviewstreet.web.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@Secured(UserRoleConstants.RESPONDENT)
public class RespondentController {

    @Autowired
    UserService userService;

    @Autowired
    AnswerService answerService;

    @Autowired
    InterviewService interviewService;

    @RequestMapping(value = {"/interviews"}, method = RequestMethod.GET)
    public String showInterviews(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<UserInterview> interviews = userService.getInterviews(username);
        model.addAttribute(AttributeConstants.USER_INTERVIEWS, interviews);

        return "interviews";
    }

    @RequestMapping(value = {"/interview-questions/{id}"}, method = RequestMethod.GET)
    public String showInterviewQuestions(@PathVariable Integer id, Model model) {
        List<Form> questionForms = interviewService.getQuestions(id);
        List<List<Form>> answerForms = interviewService.getAnswers(questionForms);

        model.addAttribute(AttributeConstants.QUESTION_FORMS, questionForms);
        model.addAttribute(AttributeConstants.ANSWER_FORMS, answerForms);

        Interview interview = interviewService.get(id);
        model.addAttribute(AttributeConstants.INTERVIEW, interview);

        return "interview-questions";
    }

    @RequestMapping(value = {"/send-interview/{id}"}, method = RequestMethod.POST)
    public String sendUserForm(@PathVariable Integer id, HttpServletRequest req) {
        Interview interview = interviewService.get(id);

        List<Integer> questionIds = new ArrayList<>();
        Map<Integer, String[]> map = new HashMap<>();

        Enumeration params = req.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement().toString();
            String[] values = req.getParameterValues(param);

            Integer curId = Integer.parseInt(param);

            questionIds.add(curId);
            map.put(curId, values);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.get(username);

        answerService.insertUserAnswers(interview, questionIds, map, user);

        return "redirect:/interviews";
    }

}
