package by.gstu.interviewstreet.web.controller.action;

import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Subdivision;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.StatisticsService;
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

import java.lang.reflect.Type;
import java.util.List;

@Controller()
@RequestMapping("/statistics")
@Secured({UserRoleConstants.VIEWER})
public class StatisticsController {

    @Autowired
    InterviewService interviewService;

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    SubdivisionService subdivisionService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String showStatistics(Model model) {
        model.addAttribute(AttrConstants.INTERVIEWS, interviewService.getAll());
        return "statistics";
    }

    @RequestMapping(value = {"/{hash}"}, method = RequestMethod.GET)
    public String showInterviewStatistics(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);

        int questionCount = interview.getQuestions().size();

        if (questionCount < 1) {
            model.addAttribute(AttrConstants.NOT_ANSWERS, true);
            return "statistics";
        }

        model.addAttribute(AttrConstants.INTERVIEW, interview);
        model.addAttribute(AttrConstants.INTERVIEWS, interviewService.getAll());
        model.addAttribute(AttrConstants.STATISTICS, statisticsService.getInterviewStatistics(interview));
        model.addAttribute(AttrConstants.SUBDIVISIONS, subdivisionService.getSubdivisionsByInterview(hash));

        return "statistics";
    }

    @ResponseBody
    @RequestMapping(value = {"/load-subs"}, method = RequestMethod.GET, produces = {"text/plain; charset=UTF-8"})
    public ResponseEntity<String> loadSubs(@RequestParam String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        Interview interview = JSONParser.convertJsonElementToObject(jsonArray, Interview.class);
        List<Subdivision> subdivisions = subdivisionService.getSubdivisionsByInterview(interview.getHash());

        String jsonData = JSONParser.convertObjectToJsonString(subdivisions);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

}
