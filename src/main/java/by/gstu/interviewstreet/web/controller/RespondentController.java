package by.gstu.interviewstreet.web.controller;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.AnswerService;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.UserService;
import by.gstu.interviewstreet.web.AttributeConstants;
import by.gstu.interviewstreet.web.ParameterConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@Secured(UserRoleConstants.RESPONDENT)
public class RespondentController {



}
