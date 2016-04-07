package by.gstu.interviewstreet.web.controller;


import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttributeConstants;
import by.gstu.interviewstreet.web.util.JSONParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Controller
@Secured(UserRoleConstants.EDITOR)
public class EditorController {

    @Autowired
    public UserService userService;

    @Autowired
    public AnswerService answerService;

    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public QuestionService questionService;

    @Autowired
    public InterviewService interviewService;

    @Autowired
    public SubdivisionService subdivisionService;

    @Autowired
    public UserInterviewService userInterviewService;

    @ResponseBody
    @RequestMapping(value = {"/hide-chip"}, method = RequestMethod.GET)
    public ResponseEntity<String> hideChip(HttpSession session) {
        session.setAttribute(AttributeConstants.CHIP, false);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String showInterviewList(Model model) {
        model.addAttribute(AttributeConstants.INTERVIEWS, interviewService.getAll());
        model.addAttribute(AttributeConstants.SUBDIVISIONS, subdivisionService.getAll());

        return "interview-list";
    }

    @ResponseBody
    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> loadPosts(@RequestBody String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        Type type = new TypeToken<List<Integer>>() { }.getType();
        List<Integer> subdivisionIds = JSONParser.convertJsonElementToObject(jsonArray, type);
        List<Employee> employees = employeeService.getBySubdivisions(subdivisionIds);

        String jsonData = JSONParser.convertObjectToJsonString(employees);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/save-interview"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> processAddInterviewForm(@RequestBody String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        JsonElement interviewElement = jsonArray.get(0);
        JsonArray idsArray = jsonArray.get(1).getAsJsonArray();

        Interview interview = JSONParser.convertJsonElementToObject(interviewElement, Interview.class);
        interview = interviewService.saveOrUpdate(interview);

        if (interview.getType().isOpen()) {
            Integer[] postIds = JSONParser.convertJsonElementToObject(idsArray, Integer[].class);
            userInterviewService.addInterviewToUserByPost(interview, postIds);
        }

        String jsonData = JSONParser.convertObjectToJsonString(interview.getId());

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/delete-interview"}, method = RequestMethod.POST)
    public ResponseEntity<String> deleteInterview(@RequestParam String data) {
        Interview interview = JSONParser.convertJsonStringToObject(data, Interview.class);
        interviewService.remove(interview);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/lock-interview/{interviewId}"}, method = RequestMethod.GET)
    public ResponseEntity<String> lockOrUnlockInterview(@PathVariable int interviewId) {
        interviewService.lockOrUnlock(interviewId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/load-card-values"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> loadValuesForCard(@RequestParam int interviewId) {
        Map<String, Object> valueMap = interviewService.getValueMapForCard(interviewId);
        String jsonData = JSONParser.convertObjectToJsonString(valueMap);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{hash}/designer"}, method = RequestMethod.GET)
    public String showDesigner(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            return "404";
        }

        List<Question> questions = questionService.getAllOrderByNumber(hash);

        model.addAttribute(AttributeConstants.INTERVIEW, interview);
        model.addAttribute(AttributeConstants.QUESTIONS, questions);

        return "designer";
    }

}