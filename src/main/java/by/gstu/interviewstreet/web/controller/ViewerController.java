package by.gstu.interviewstreet.web.controller;

import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.QuestionService;
import by.gstu.interviewstreet.web.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ViewerController {

    @Autowired
    InterviewService interviewService;

    @Autowired
    QuestionService questionService;

    @RequestMapping(value = {"/statistics"}, method = RequestMethod.GET)
    public String showStatistics() {
        return "statistics";
    }

    @RequestMapping(value = {"/load-interviews/{typeId}"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String loadInterviews(@PathVariable int typeId) {
        try {
            List<Interview> interviews = interviewService.getByType(typeId);
            return interviewService.getLightJSON(interviews);
        } catch (RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }
    }

    @RequestMapping(value = {"/load-questions/{interviewId}"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String loadQuestions(@PathVariable int interviewId) {
        try {
            List<Form> questionForms = interviewService.getQuestions(interviewId);
            return questionService.getJSON(questionForms);
        } catch (RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }
    }

    @RequestMapping(value = {"/load-subdivisions/{interviewId}"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String loadSubdivisions(@PathVariable int interviewId) {
        try {
            Interview interview = interviewService.get(interviewId);
            return interviewService.getJSON(interview);
        } catch (RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }
    }

}
