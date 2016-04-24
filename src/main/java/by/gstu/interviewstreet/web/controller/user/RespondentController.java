package by.gstu.interviewstreet.web.controller.user;

import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.UserAnswerService;
import by.gstu.interviewstreet.service.UserInterviewService;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.WebConstants;
import by.gstu.interviewstreet.web.util.ControllerUtils;
import by.gstu.interviewstreet.web.util.JSONParser;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/respondent")
@Secured({UserRoleConstants.RESPONDENT, UserRoleConstants.EDITOR})
public class RespondentController extends UserController {

    @Autowired
    InterviewService interviewService;

    @Autowired
    UserAnswerService userAnswerService;

    @Autowired
    UserInterviewService userInterviewService;

    @RequestMapping("/dashboard")
    public String showDashboard(Principal principal, Model model) {
        User user = getUserByPrincipal(principal);
        List<UserInterview> availableInterviews = ControllerUtils.getAvailableInterviews(user.getInterviewsForPassing());

        model.addAttribute(AttrConstants.USER_INTERVIEWS, availableInterviews);

        return "dashboard";
    }

    @RequestMapping(value = "/{hash}/interview", method = RequestMethod.GET)
    public String showDashboard(@PathVariable String hash, Principal principal, Model model) {
        UserInterview userInterview = userInterviewService.getByUserAndInterview(principal.getName(), hash);
        if (userInterview == null || userInterview.getPassed()) {
            return "redirect:/dashboard";
        }

        Interview interview = userInterview.getInterview();
        List<Question> questions = interview.getQuestions();

        Collections.sort(questions);

        model.addAttribute(AttrConstants.INTERVIEW, interview);
        model.addAttribute(AttrConstants.QUESTIONS, questions);

        return "interview";
    }

    @RequestMapping(value = "interview/{hash}/anonymous", method = RequestMethod.GET)
    public String showDashboard(@PathVariable String hash, Model model, HttpServletRequest request) {

        Interview interview = interviewService.get(hash);
        List<Question> questions = interview.getQuestions();

        Collections.sort(questions);

        model.addAttribute(AttrConstants.INTERVIEW, interview);
        model.addAttribute(AttrConstants.QUESTIONS, questions);

        return "interview";
    }

    @ResponseBody
    @RequestMapping(value = "/send/interview", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> sendInterview(String hash, String data, Principal principal) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            return new ResponseEntity<>(WebConstants.USER_SEND_WRONG_HASH_MSG + hash, HttpStatus.BAD_REQUEST);
        }

        if (interview.getHide()) {
            return new ResponseEntity<>(WebConstants.USER_SEND_CLOSED_INTERVIEW_MSG, HttpStatus.NOT_ACCEPTABLE);
        }

        Type type = new TypeToken<List<Answer>>() {
        }.getType();
        List<Answer> answers = JSONParser.convertJsonStringToObject(data, type);

        try {
            User user = getUserByPrincipal(principal);
            userAnswerService.save(user, interview, answers);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(WebConstants.USER_SEND_PASSED_INTERVIEW_MSG, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
