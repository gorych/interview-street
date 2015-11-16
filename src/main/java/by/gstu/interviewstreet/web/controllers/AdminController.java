package by.gstu.interviewstreet.web.controllers;


import by.gstu.interviewstreet.service.AnswerService;
import by.gstu.interviewstreet.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String goToInterviews() {
        return "interview-list";
    }

    @RequestMapping(value = {"/questions-editor"}, method = RequestMethod.GET)
    public String goToQuestionsEditor() {
        return "questions-editor";
    }

    @RequestMapping(value = {"/create-question"}, method = RequestMethod.GET)
    @ResponseBody
    public long createNewQuestion() {
        return questionService.insertQuestion();
    }

    @RequestMapping(value = {"/create-answer"}, method = RequestMethod.GET)
    @ResponseBody
    public long createNewAnswer() {
        return answerService.insertAnswer();
    }
}
