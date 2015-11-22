package by.gstu.interviewstreet.web.controllers;

import by.gstu.interviewstreet.security.UserRoleConstants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

@Controller
@Secured(UserRoleConstants.RESPONDENT)
public class RespondentController {



}
