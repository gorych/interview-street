package by.gstu.interviewstreet.web.controller;


import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttributeConstants;
import by.gstu.interviewstreet.web.util.JSONParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Controller
//@Secured(UserRoleConstants.EDITOR)
public class EditorController {

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

    @ResponseBody
    @RequestMapping(value = {"/build-form"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public String buildQuestionForm(int hash, int answerTypeId) {
        AnswerType answerType;
        Interview interview;
        try {
            answerType = answerService.getAnswerType(answerTypeId);
            interview = interviewService.get(hash);
        } catch (RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }

        if (interview == null || answerType == null) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }

        Map<String, Object> valueMap = null;//formService.getValueMapForForm(interview, answerType);

        return JSONParser.convertObjectToJsonString(valueMap);
    }

    @RequestMapping(value = {"/{interviewId}/designer"}, method = RequestMethod.GET)
    public String showQuestionsEditor(@PathVariable int interviewId, Model model) {

        return "test";
    }

    //endregion
}