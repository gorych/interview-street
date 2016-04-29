package by.gstu.interviewstreet.web.controller.user;

import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.SortType;
import by.gstu.interviewstreet.service.UserInterviewService;
import by.gstu.interviewstreet.web.AttrConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/viewer")
@Secured({UserRoleConstants.VIEWER})
public class ViewerController {

    @Autowired
    UserInterviewService userInterviewService;

    @RequestMapping(value = {"/{hash}/respondents"}, method = RequestMethod.GET)
    public String showRespondents(@PathVariable String hash, @RequestParam SortType sortType, Model model) {
        List<UserInterview> userInterviews = userInterviewService.getByInterviewHash(hash);

        model.addAttribute(AttrConstants.USER_INTERVIEWS, userInterviews);

        return "respondents";
    }



}

