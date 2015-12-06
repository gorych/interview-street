package by.gstu.interviewstreet.web.controller;

import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.AnswerService;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.UserService;
import by.gstu.interviewstreet.web.AttributeConstants;
import by.gstu.interviewstreet.web.ParameterConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
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

    @Secured(UserRoleConstants.ANONYMOUS)
    @RequestMapping(value = {"/interview-street/{hash}/anonymous"}, method = RequestMethod.GET)
    public String showAnonymousInterviews(@PathVariable long hash, Model model) {
        List<Form> questionForms = interviewService.getQuestions(hash);
        List<List<Form>> answerForms = interviewService.getAnswers(questionForms);

        model.addAttribute(AttributeConstants.QUESTION_FORMS, questionForms);
        model.addAttribute(AttributeConstants.ANSWER_FORMS, answerForms);

        Interview interview = interviewService.get(hash);
        model.addAttribute(AttributeConstants.INTERVIEW, interview);

        return "anonymous-interviews";
    }

    @RequestMapping(value = {"/user-interviews/{id}"}, method = RequestMethod.GET)
    public String showInterviewQuestions(@PathVariable Integer id, Model model) {
        List<Form> questionForms = interviewService.getQuestions(id);
        List<List<Form>> answerForms = interviewService.getAnswers(questionForms);

        model.addAttribute(AttributeConstants.QUESTION_FORMS, questionForms);
        model.addAttribute(AttributeConstants.ANSWER_FORMS, answerForms);

        Interview interview = interviewService.get(id);
        model.addAttribute(AttributeConstants.INTERVIEW, interview);

        return "user-interviews";
    }

    @RequestMapping(value = {"/send-interview/{id}"}, method = RequestMethod.POST)
    @ResponseBody
    public String sendUserForm(@PathVariable Integer id, HttpServletRequest req) {
        try {
            List<Integer> questionIds = new ArrayList<>();
            Map<Integer, String[]> map = new HashMap<>();

            Interview interview = interviewService.get(id);
            Enumeration params = req.getParameterNames();

            while (params.hasMoreElements()) {
                String param = params.nextElement().toString();
                if (ParameterConstants.INTERVIEW_ID.equals(param)) {
                    continue;
                }
                String[] values = req.getParameterValues(param);
                Integer curId = Integer.parseInt(param);

                questionIds.add(curId);
                map.put(curId, values);
            }
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.get(username);

            answerService.insertUserAnswers(interview, questionIds, map, user);
            return AttributeConstants.SUCCESS_RESPONSE_BODY;
        } catch (RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }
    }

}
