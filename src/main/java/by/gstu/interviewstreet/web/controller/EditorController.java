package by.gstu.interviewstreet.web.controller;


import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttributeConstants;
import by.gstu.interviewstreet.web.util.JSONParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@Secured(UserRoleConstants.EDITOR)
public class EditorController {

    @Autowired
    UserService userService;

    @Autowired
    AnswerService answerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    QuestionService questionService;

    @Autowired
    InterviewService interviewService;

    @Autowired
    IInterviewTypeDAO interviewTypeDAO;

    @Autowired
    SubdivisionService subdivisionService;

    @Autowired
    UserInterviewService userInterviewService;

    @ModelAttribute(AttributeConstants.USER_INITIALS)
    public String addUserInitials(Principal principal) {
        String username = principal.getName();
        User user = userService.get(username);

        return user.getEmployee().getInitials();
    }

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String showInterviewList(Model model) {
        model.addAttribute(AttributeConstants.INTERVIEWS, interviewService.getAll());
        model.addAttribute(AttributeConstants.SUBDIVISIONS, subdivisionService.getAll());

        return "interview-list";
    }

    @ResponseBody
    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public String loadPosts(@RequestBody String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        Type type = new TypeToken<List<Integer>>() { }.getType();
        List<Integer> subdivisionIds = JSONParser.convertJsonElementToObject(jsonArray, type);
        List<Employee> employees = employeeService.getBySubdivisions(subdivisionIds);

        return JSONParser.convertObjectToJsonString(employees);
    }

    @ResponseBody
    @RequestMapping(value = {"/save-interview"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public String processAddInterviewForm(@RequestBody String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        JsonElement interviewElement = jsonArray.get(0);
        JsonArray idsArray = jsonArray.get(1).getAsJsonArray();

        Interview interview = JSONParser.convertJsonElementToObject(interviewElement, Interview.class);
        interview = interviewService.saveOrUpdate(interview);

        if (interview.getType().isOpen()) {
            Integer[] postIds = JSONParser.convertJsonElementToObject(idsArray, Integer[].class);
            userInterviewService.addInterviewToUserByPost(interview, postIds);
        }

        return JSONParser.convertObjectToJsonString(interview.getId());
    }

    @ResponseBody
    @RequestMapping(value = {"/delete-interview"}, method = RequestMethod.POST)
    public String deleteInterview(@RequestParam String data) {
        Interview interview = JSONParser.convertJsonStringToObject(data, Interview.class);
        interviewService.remove(interview);

        return AttributeConstants.SUCCESS_RESPONSE_BODY;
    }

    @ResponseBody
    @RequestMapping(value = {"/lock-interview/{interviewId}"}, method = RequestMethod.GET)
    public String lockOrUnlockInterview(@PathVariable int interviewId) {
        interviewService.lockOrUnlock(interviewId);

        return AttributeConstants.SUCCESS_RESPONSE_BODY;
    }

    @ResponseBody
    @RequestMapping(value = {"/load-card-values"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    public String loadValuesForCard(@RequestParam int interviewId) {
        Map<String, Object> valueMap = interviewService.getValueMapForCard(interviewId);

        return JSONParser.convertObjectToJsonString(valueMap);
    }

    @RequestMapping(value = {"/{hash}/designer"}, method = RequestMethod.GET)
    public String showDesigner(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);
        List<Question> questions = questionService.getAllOrderByNumber(hash);

        model.addAttribute(AttributeConstants.INTERVIEW, interview);
        model.addAttribute(AttributeConstants.QUESTIONS, questions);

        return "designer";
    }

    @ResponseBody
    @RequestMapping(value = {"/designer/add-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public String addQuestion(String hash, int answerTypeId, int number) {
        try {
            AnswerType answerType = answerService.getAnswerType(answerTypeId);
            Interview interview = interviewService.get(hash);

            if (interview == null || answerType == null) {
                return AttributeConstants.ERROR_RESPONSE_BODY;
            }

            Question question = questionService.add(interview, number);
            Map<String, Object> valueMap = interviewService.getValueMapForQuestionForm(question, answerType);

            return JSONParser.convertObjectToJsonString(valueMap);
        } catch (RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/designer/del-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public String removeQuestion(@RequestParam int id) {
        try {
            Question question = questionService.get(id);
            questionService.remove(question);

            return AttributeConstants.SUCCESS_RESPONSE_BODY;
        } catch (RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/designer/move-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public String moveQuestion(@RequestParam int id, @RequestParam int number) {
        try {
            questionService.move(id ,number);

            return AttributeConstants.SUCCESS_RESPONSE_BODY;
        } catch (RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }
    }

    //endregion
}