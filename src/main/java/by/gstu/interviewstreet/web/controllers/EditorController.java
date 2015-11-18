package by.gstu.interviewstreet.web.controllers;


import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.InterviewType;
import by.gstu.interviewstreet.domain.Subdivision;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Controller
public class EditorController {

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    InterviewService interviewService;

    @Autowired
    InterviewTypeService interviewTypeService;

    @Autowired
    SubdivisionService subdivisionService;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String goToInterviewList(Model model) {
        final int RECTORATE_ID = 1;

        List<Subdivision> subdivisions = subdivisionService.getAllSubdivisions();
        List<Employee> employees = employeeService.getEmployeesBySubdivision(RECTORATE_ID);

        model.addAttribute(AttributeConstants.INTERVIEW, new Interview());
        model.addAttribute(AttributeConstants.SUBDIVISIONS, subdivisions);
        model.addAttribute(AttributeConstants.EMPLOYEES, employees);
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

    @RequestMapping(value = {"/create-interview"}, method = RequestMethod.GET)
    public String createInterview() {
        return "interview-list";
    }

    @RequestMapping(value = {"/create-interview"}, method = RequestMethod.POST)
    public String createInterview(@Valid Interview interview) {
        Calendar cal = Calendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        Date currentDate = new Date(utilDate.getTime());

        int answerTypeId = interview.getType().getId();
        InterviewType type = interviewTypeService.getInterviewTypeById(answerTypeId);

        interview.setPlacementDate(currentDate);
        interview.setType(type);

        interviewService.insertInterview(interview);

        return "interview-list";
    }

    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.POST)
    public void loadPosts(@RequestParam(value = "ids") int[] ids) {
        System.out.println("YES");
        for (int id : ids) {
            System.out.println(id);
        }
    }
}
