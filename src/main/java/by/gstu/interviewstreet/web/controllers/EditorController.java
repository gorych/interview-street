package by.gstu.interviewstreet.web.controllers;


import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.helpers.UserInterviewHelper;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Controller
public class EditorController {

    @Autowired
    UserService userService;

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

    @ModelAttribute(AttributeConstants.USER_INTERVIEW_HELPER)
    public UserInterviewHelper loadEmptyUserInterviewBean() {
        return new UserInterviewHelper();
    }

    @ModelAttribute(AttributeConstants.INTERVIEWS)
    public List<Interview> loadInterviews() {
        return interviewService.getAllInterviews();
    }

    @ModelAttribute(AttributeConstants.SUBDIVISIONS)
    public List<Subdivision> loadSubdivisions() {
        return subdivisionService.getAllSubdivisions();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, AttributeConstants.POSTS, new CustomCollectionEditor(Set.class) {
            Post post = null;

            @Override
            protected Object convertElement(Object element) {
                try {
                    int id = Integer.parseInt((String) element);
                    post = new Post(id);
                } catch (NumberFormatException e) {
                    System.out.println("Element " + element + " is incorrect.");
                    e.printStackTrace();
                }
                return post;
            }
        });
    }

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String goToInterviewList() {
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

    @RequestMapping(value = {"/create-interview"}, method = RequestMethod.POST)
    public String createInterview(UserInterviewHelper helper) {
        Set<Post> posts = helper.getPosts();
        if (posts == null) {
            return "redirect:/interview-list";
        }

        System.out.println(helper);

        List<Integer> ids = new ArrayList<>();
        for (Post post : posts) {
            ids.add(post.getId());
        }

        List<User> users = userService.getUsersByPosts(ids);
        Interview interview = helper.getInterview();

        Calendar calender = Calendar.getInstance();
        java.util.Date utilDate = calender.getTime();
        Date currentDate = new Date(utilDate.getTime());

        int answerTypeId = interview.getType().getId();
        InterviewType type = interviewTypeService.getInterviewTypeById(answerTypeId);

        interview.setPlacementDate(currentDate);
        interview.setType(type);

        interviewService.insertInterview(interview, users);

        return "redirect:/interview-list";
    }

    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String loadPosts(@RequestParam String data) {
        String[] strValues = data.split(",");
        Object[] ids = new Object[strValues.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = Integer.parseInt(strValues[i]);
        }

        List<Employee> employees = employeeService.getEmployeesBySubdivision(ids);
        return employeeService.getJsonString(employees);
    }

    @RequestMapping(value = {"/delete-hide-interview"}, method = RequestMethod.GET)
    public String deleteInterview(@RequestParam(value = "id", required = false) int[] ids,
                                  @RequestParam(value = "interviewId", required = false) int id,
                                  @RequestParam(value = "operation", required = true) String operation) {
        switch (operation) {
            case "delete":
                interviewService.removeInterviews(ids);
                break;
            case "hide":
                interviewService.hideInterview(id);
                break;
            default:
                //NOP
                break;
        }

        return "redirect:/interview-list";
    }

    @RequestMapping(value = {"/edit-interview"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String editInterviewModal(@RequestParam(value = "interviewId") int interviewId) {
        return interviewService.getJsonString(interviewId);
    }

}
