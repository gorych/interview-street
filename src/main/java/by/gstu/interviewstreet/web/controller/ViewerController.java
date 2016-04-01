package by.gstu.interviewstreet.web.controller;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.AnswerService;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.QuestionService;
import by.gstu.interviewstreet.web.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Secured(UserRoleConstants.VIEWER)
public class ViewerController {



}
