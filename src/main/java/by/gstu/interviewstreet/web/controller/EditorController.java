package by.gstu.interviewstreet.web.controller;


import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.EmployeeService;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.QuestionService;
import by.gstu.interviewstreet.service.SubdivisionService;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.util.JSONParser;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.List;

@Controller
@RequestMapping("/editor")
@Secured(UserRoleConstants.EDITOR)
public class EditorController extends UserController {

    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public QuestionService questionService;

    @Autowired
    public InterviewService interviewService;

    @Autowired
    public SubdivisionService subdivisionService;

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String showInterviewList(Model model) {
        final int BEGIN_INDEX = 0;
        final int RECORD_COUNT = 6;

        model.addAttribute(AttrConstants.INTERVIEWS, interviewService.getAllInRange(BEGIN_INDEX, RECORD_COUNT));
        model.addAttribute(AttrConstants.SUBDIVISIONS, subdivisionService.getAll());

        return "interview-list";
    }

    @ResponseBody
    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> loadPosts(@RequestBody String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> subdivisionIds = JSONParser.convertJsonElementToObject(jsonArray, type);
        List<Employee> employees = employeeService.getBySubdivisions(subdivisionIds);

        String jsonData = JSONParser.convertObjectToJsonString(employees);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{hash}/designer"}, method = RequestMethod.GET)
    public String showDesigner(@PathVariable String hash, HttpServletResponse response, Model model) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            return "404";
        }

        response.addCookie(new Cookie("hash", hash));

        List<Question> questions = questionService.getAllOrderByNumber(hash);

        model.addAttribute(AttrConstants.INTERVIEW, interview);
        model.addAttribute(AttrConstants.QUESTIONS, questions);

        return "designer";
    }

}