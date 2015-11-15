package by.gstu.interviewstreet.web.controllers;

import by.gstu.interviewstreet.security.UserRoleConstants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Secured(UserRoleConstants.RESPONDENT)
public class RespondentController {

    @RequestMapping(value = {"/interviews"}, method = RequestMethod.GET)
    public String goToInterviews() {
        return "interviews";
    }

}
