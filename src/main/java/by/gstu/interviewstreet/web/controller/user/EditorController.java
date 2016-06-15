package by.gstu.interviewstreet.web.controller.user;


import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.EmployeeService;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.QuestionService;
import by.gstu.interviewstreet.service.SubdivisionService;
import by.gstu.interviewstreet.util.JSONParser;
import by.gstu.interviewstreet.util.WebUtils;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.SecurityConstants;
import by.gstu.interviewstreet.web.WebConstants;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static by.gstu.interviewstreet.web.WebConstants.ENCODING_PRODUCE;

@Controller
@RequestMapping("/editor")
@Secured(SecurityConstants.EDITOR)
public class EditorController extends UserController {
    private static final Logger LOG = LoggerFactory.getLogger(EditorController.class);

    private static final int START_PAGE_NUMBER = 1;
    private static final int CARD_COUNT_PER_PAGE = 6;

    private static final int LEFT_BOUND_INDEX = 0;
    private static final int RIGHT_BOUND_INDEX = 1;

    @Autowired
    private  EmployeeService employeeService;

    @Autowired
    private  QuestionService questionService;

    @Autowired
    private  InterviewService interviewService;

    @Autowired
    private  SubdivisionService subdivisionService;

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String showInterviewList(@RequestParam(required = false) Integer pageNumber, Model model, Principal principal) {
        User user = getUserByPrincipal(principal);
        List<Subdivision> subs = subdivisionService.getAll();
        List<Interview> allInterviews = user.getCreatedInterviews();

        int size = allInterviews.size();
        int pageCount = WebUtils.getPageCount(size, CARD_COUNT_PER_PAGE);

        if (pageNumber == null || pageNumber > pageCount || pageNumber < 0) {
            pageNumber = START_PAGE_NUMBER;
        }

        int[] bounds = WebUtils.getPaginationBounds(pageNumber, pageCount,
                CARD_COUNT_PER_PAGE, START_PAGE_NUMBER);

        int fromIndex = CARD_COUNT_PER_PAGE * (pageNumber - 1);
        int lastIndex = fromIndex + CARD_COUNT_PER_PAGE;
        int toIndex = lastIndex <= size ? lastIndex : size;

        List<Interview> interviewsForPage = allInterviews.subList(fromIndex, toIndex);
        Collections.sort(interviewsForPage);

        model.addAttribute(AttrConstants.PAGE_COUNT, pageCount);
        model.addAttribute(AttrConstants.START_PAGE_NUMBER, bounds[LEFT_BOUND_INDEX]);
        model.addAttribute(AttrConstants.LAST_PAGE_NUMBER, bounds[RIGHT_BOUND_INDEX]);
        model.addAttribute(AttrConstants.ACTIVE_PAGE_NUMBER, pageNumber);

        model.addAttribute(AttrConstants.INTERVIEWS, interviewsForPage);
        model.addAttribute(AttrConstants.SUBDIVISIONS, subs);

        return "interview-list";
    }

    @RequestMapping(value = {"/{hash}/designer"}, method = RequestMethod.GET)
    public String showDesigner(@PathVariable String hash, HttpServletResponse response, Model model) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            LOG.warn("User try change nonexistent interview. Hash is " + hash);
            return "error/404";
        }

        response.addCookie(new Cookie(WebConstants.HASH, hash));

        List<Question> questions = questionService.getAllOrderByNumber(hash);

        model.addAttribute(AttrConstants.INTERVIEW, interview);
        model.addAttribute(AttrConstants.QUESTIONS, questions);

        return "designer";
    }

    @ResponseBody
    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.POST, produces = {ENCODING_PRODUCE})
    public ResponseEntity<String> loadPosts(@RequestBody String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        Type type = new TypeToken<List<Integer>>() { }.getType();
        List<Integer> subdivisionIds = JSONParser.convertJsonElementToObject(jsonArray, type);
        List<Employee> employees = employeeService.getBySubdivisions(subdivisionIds);

        String jsonData = JSONParser.convertObjectToJsonString(employees);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

}