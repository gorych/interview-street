package by.gstu.interviewstreet.web.controller.action;

import by.gstu.interviewstreet.bean.StatisticData;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.PublishedInterview;
import by.gstu.interviewstreet.domain.Subdivision;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.StatisticsService;
import by.gstu.interviewstreet.service.SubdivisionService;
import by.gstu.interviewstreet.service.UserAnswerService;
import by.gstu.interviewstreet.util.JSONParser;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.gstu.interviewstreet.web.WebConstants.ENCODING_PRODUCE;

@Controller()
@RequestMapping("/statistics")
@Secured({SecurityConstants.EDITOR})
public class StatisticsController {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private SubdivisionService subdivisionService;

    @Autowired
    private UserAnswerService userAnswerService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String showStatistics(Model model) {
        model.addAttribute(AttrConstants.INTERVIEWS, interviewService.getAll());
        return "statistics";
    }

    @RequestMapping(value = {"/{hash}"}, method = RequestMethod.GET)
    public String showInterviewStatistics(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            return "error/404";
        }

        int questionCount = interview.getQuestions().size();

        if (questionCount < 1) {
            model.addAttribute(AttrConstants.NOT_ANSWERS, true);
            return "statistics";
        }

        model.addAttribute(AttrConstants.INTERVIEW, interview);
        model.addAttribute(AttrConstants.INTERVIEWS, interviewService.getAll());
        model.addAttribute(AttrConstants.PUBLISHED_INTERVIEWS, interviewService.getPublishedInterviews(interview));
        /*NULL is correct value*/
        model.addAttribute(AttrConstants.STATISTICS, statisticsService.getInterviewStatistics(interview, null, null));
        model.addAttribute(AttrConstants.SUBDIVISIONS, subdivisionService.getSubdivisionsByInterview(hash));

        return "statistics";
    }

    @ResponseBody
    @RequestMapping(value = {"/load-data"}, method = RequestMethod.GET, produces = {ENCODING_PRODUCE})
    public ResponseEntity<String> loadStatistics(@RequestParam String hash,
                                                 @RequestParam(required = false) Integer subId,
                                                 @RequestParam(required = false) Integer publishId) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            return new ResponseEntity<>(JSONParser.convertObjectToJsonString(StringUtils.EMPTY), HttpStatus.OK);
        }

        Subdivision subdivision = subdivisionService.getById(subId);
        PublishedInterview publish = interviewService.getPublish(publishId);

        List<StatisticData> statistics = statisticsService.getInterviewStatistics(interview, subdivision, publish);

        Map<String, Object> data = new HashMap<>();
        data.put(AttrConstants.STATISTICS, statistics);

        if (subdivision == null) {
            List<Subdivision> subs = subdivisionService.getSubdivisionsByInterview(hash);
            data.put(AttrConstants.SUBDIVISIONS, subs);
        }

        if (publish == null) {
            List<PublishedInterview> publishes = interviewService.getPublishedInterviews(interview);
            data.put(AttrConstants.PUBLISHED_DATES, publishes);
        }

        String jsonData = JSONParser.convertObjectToJsonString(data);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/load-respondents"}, method = RequestMethod.GET, produces = {ENCODING_PRODUCE})
    public ResponseEntity<String> getRespondentList(String hash, String answerText) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            return new ResponseEntity<>("User try getting information about nonexistent interview.", HttpStatus.BAD_REQUEST);
        }

        if (interview.isClosedType()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        List<String> respondentList = userAnswerService.getRespondentList(interview, answerText);
        String jsonData = JSONParser.convertObjectToJsonString(respondentList);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

}