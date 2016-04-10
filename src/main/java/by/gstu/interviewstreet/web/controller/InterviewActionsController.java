package by.gstu.interviewstreet.web.controller;

import by.gstu.interviewstreet.dao.InterviewTypeDAO;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.SubdivisionService;
import by.gstu.interviewstreet.service.UserInterviewService;
import by.gstu.interviewstreet.web.AttributeConstants;
import by.gstu.interviewstreet.web.util.JSONParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/interview")
@Secured(UserRoleConstants.EDITOR)
public class InterviewActionsController {

    @Autowired
    InterviewTypeDAO interviewTypeDAO;

    @Autowired
    public InterviewService interviewService;

    @Autowired
    public SubdivisionService subdivisionService;

    @Autowired
    public UserInterviewService userInterviewService;

    @RequestMapping(value = {"/form"}, method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute(AttributeConstants.INTERVIEW, new Interview());
        model.addAttribute(AttributeConstants.SUBDIVISIONS, subdivisionService.getAll());

        model.addAttribute(AttributeConstants.OPEN_TYPE, interviewTypeDAO.getByName("open"));
        model.addAttribute(AttributeConstants.CLOSE_TYPE, interviewTypeDAO.getByName("close"));
        model.addAttribute(AttributeConstants.EXPERT_TYPE, interviewTypeDAO.getByName("expert"));

        return "form";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    @ResponseBody
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

        return new ResponseEntity<>(interview.getHash(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    public ResponseEntity<String> deleteInterview(@RequestParam String data) {
        Interview interview = JSONParser.convertJsonStringToObject(data, Interview.class);
        interviewService.remove(interview);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/lock/{interviewId}"}, method = {RequestMethod.GET})
    public ResponseEntity<String> lockOrUnlockInterview(@PathVariable int interviewId) {
        interviewService.lockOrUnlock(interviewId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
