package by.gstu.interviewstreet.web.controller.user;


import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.WebConstants;
import by.gstu.interviewstreet.web.util.ControllerUtils;
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
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/editor")
@Secured(UserRoleConstants.EDITOR)
public class EditorController extends UserController {

    private static final int START_PAGE_NUMBER = 1;
    private static final int CARD_COUNT_PER_PAGE = 6;

    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public QuestionService questionService;

    @Autowired
    public InterviewService interviewService;

    @Autowired
    public SubdivisionService subdivisionService;

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String showInterviewList(@RequestParam(required = false) Integer pageNumber, Model model, Principal principal) {
        User user = getUserByPrincipal(principal);
        List<Subdivision> subs = subdivisionService.getAll();
        List<Interview> allInterviews = user.getCreatedInterviews();

        int size = allInterviews.size();
        int pageCount = ControllerUtils.getPageCount(size, CARD_COUNT_PER_PAGE);

        if (pageNumber == null || pageNumber > pageCount || pageNumber < 0) {
            pageNumber = START_PAGE_NUMBER;
        }

        int[] bounds = ControllerUtils.getPaginationBounds(pageNumber, pageCount,
                CARD_COUNT_PER_PAGE, START_PAGE_NUMBER);

        int fromIndex = CARD_COUNT_PER_PAGE * (pageNumber - 1);
        int lastIndex = fromIndex + CARD_COUNT_PER_PAGE;
        int toIndex = lastIndex <= size ? lastIndex : size;

        List<Interview> interviewsForPage = allInterviews.subList(fromIndex, toIndex);
        Collections.sort(interviewsForPage);

        model.addAttribute(AttrConstants.PAGE_COUNT, pageCount);
        model.addAttribute(AttrConstants.START_PAGE_NUMBER, bounds[0]);
        model.addAttribute(AttrConstants.LAST_PAGE_NUMBER, bounds[1]);
        model.addAttribute(AttrConstants.ACTIVE_PAGE_NUMBER, pageNumber);

        model.addAttribute(AttrConstants.INTERVIEWS, interviewsForPage);
        model.addAttribute(AttrConstants.SUBDIVISIONS, subs);

        return "interview-list";
    }

    @RequestMapping(value = {"/{hash}/designer"}, method = RequestMethod.GET)
    public String showDesigner(@PathVariable String hash, HttpServletResponse response, Model model) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            return "error/404";
        }

        response.addCookie(new Cookie(WebConstants.HASH, hash));

        List<Question> questions = questionService.getAllOrderByNumber(hash);

        model.addAttribute(AttrConstants.INTERVIEW, interview);
        model.addAttribute(AttrConstants.QUESTIONS, questions);

        return "designer";
    }

    @RequestMapping(value = {"/{hash}/preview"}, method = RequestMethod.GET)
    public String showDesigner(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);
        List<Question> questions = questionService.getAllOrderByNumber(hash);

        model.addAttribute(AttrConstants.INTERVIEW, interview);
        model.addAttribute(AttrConstants.QUESTIONS, questions);

        return "preview";
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

}