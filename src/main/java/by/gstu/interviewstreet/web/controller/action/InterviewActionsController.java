package by.gstu.interviewstreet.web.controller.action;

import by.gstu.interviewstreet.dao.InterviewTypeDAO;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.web.SecurityConstants;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.util.JSONParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/interview")
@Secured(SecurityConstants.EDITOR)
public class InterviewActionsController {

    private static final int INTERVIEW_INDEX = 0;
    private static final int POST_IDS_INDEX = 1;
    private static final int SUB_IDS_INDEX = 2;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private InterviewTypeDAO interviewTypeDAO;

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private SubdivisionService subdivisionService;

    @Autowired
    private UserInterviewService userInterviewService;

    @RequestMapping(value = {"/form"}, method = RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {
        if (id != null && id > 0) {
            model.putAll(interviewService.getModelMapForEditForm(id));
            model.addAttribute(AttrConstants.EDIT_MODE, true);
            return "form";
        }

        model.addAttribute(AttrConstants.INTERVIEW, new Interview());
        model.addAttribute(AttrConstants.POSTS, postService.getAll());
        model.addAttribute(AttrConstants.SUBDIVISIONS, subdivisionService.getAll());

        model.addAttribute(AttrConstants.OPEN_TYPE, interviewTypeDAO.getByName("open"));
        model.addAttribute(AttrConstants.CLOSE_TYPE, interviewTypeDAO.getByName("close"));
        model.addAttribute(AttrConstants.EXPERT_TYPE, interviewTypeDAO.getByName("expert"));

        return "form";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> processAddInterviewForm(@RequestBody String data, Principal principal) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);
        JsonElement interviewElement = jsonArray.get(INTERVIEW_INDEX);

        Interview interview = JSONParser.convertJsonElementToObject(interviewElement, Interview.class);
        User creator = userService.get(principal.getName());
        interview.setCreator(creator);

        interview = interviewService.saveOrUpdate(interview);

        if (interview.isOpenType()) {
            JsonArray postIdsArray = jsonArray.get(POST_IDS_INDEX).getAsJsonArray();
            JsonArray subIdsArray = jsonArray.get(SUB_IDS_INDEX).getAsJsonArray();

            Integer[] postIds = JSONParser.convertJsonElementToObject(postIdsArray, Integer[].class);
            Integer[] subIds = JSONParser.convertJsonElementToObject(subIdsArray, Integer[].class);
            userInterviewService.addInterviewToUserByPost(interview, postIds, subIds);
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

    @ResponseBody
    @RequestMapping(value = {"/update-introductory-text"}, method = RequestMethod.POST)
    public ResponseEntity<String> updateIntroductoryText(@RequestParam String hash, String text) {
        Interview interview = interviewService.get(hash);
        interview.setIntroductoryText(text);
        interviewService.update(interview);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
