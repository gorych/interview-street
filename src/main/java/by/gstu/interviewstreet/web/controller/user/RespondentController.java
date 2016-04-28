package by.gstu.interviewstreet.web.controller.user;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.UserAnswerService;
import by.gstu.interviewstreet.service.UserInterviewService;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.WebConstants;
import by.gstu.interviewstreet.web.util.DateUtils;
import by.gstu.interviewstreet.web.util.JSONParser;
import by.gstu.interviewstreet.web.util.WebUtils;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/respondent")
public class RespondentController extends UserController {

    @Autowired
    InterviewService interviewService;

    @Autowired
    UserAnswerService userAnswerService;

    @Autowired
    UserInterviewService userInterviewService;

    @Secured({UserRoleConstants.RESPONDENT, UserRoleConstants.ANONYMOUS})
    @RequestMapping("/{hash}/success")
    public String showSuccessPage(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);
        model.addAttribute(AttrConstants.SHOW_LINK, !interview.isClosedType());

        return "success";
    }

    @Secured({UserRoleConstants.RESPONDENT})
    @RequestMapping("/dashboard")
    public String showDashboard(Principal principal, Model model) {
        User user = getUserByPrincipal(principal);
        List<UserInterview> availableInterviews = WebUtils.getAvailableInterviews(user.getInterviewsForPassing());

        model.addAttribute(AttrConstants.USER_INTERVIEWS, availableInterviews);

        return "dashboard";
    }

    @Secured({UserRoleConstants.RESPONDENT})
    @RequestMapping(value = "/{hash}/interview", method = RequestMethod.GET)
    public String showInterview(@PathVariable String hash, Principal principal, Model model) {
        UserInterview uInterview = userInterviewService.getByUserAndInterview(principal.getName(), hash);
        if (uInterview == null || (!uInterview.getInterview().isSecondPassage() && uInterview.getPassed())) {
            return "redirect:/dashboard";
        }

        Interview interview = uInterview.getInterview();
        WebUtils.buildModelForDashboard(model, interview);

        return "interview";
    }

    @Secured({UserRoleConstants.RESPONDENT, UserRoleConstants.ANONYMOUS})
    @RequestMapping(value = "interview/{hash}/anonymous", method = RequestMethod.GET)
    public String showInterview(@PathVariable String hash, Model model, HttpServletRequest request) {
        Interview interview = interviewService.get(hash);
        if (!interview.isClosedType() || interview.getHide() || interview.getIsDeadline()) {
            return "error/404";
        }

        WebUtils.buildModelForDashboard(model, interview);

        return "interview";
    }

    @ResponseBody
    @Secured({UserRoleConstants.RESPONDENT, UserRoleConstants.ANONYMOUS})
    @RequestMapping(value = "/send/interview", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> sendInterview(String hash, String data,
                                                Principal principal, HttpServletResponse response) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            return new ResponseEntity<>(WebConstants.USER_SEND_WRONG_HASH_MSG + hash, HttpStatus.BAD_REQUEST);
        }

        if (interview.getHide()) {
            return new ResponseEntity<>(WebConstants.USER_SEND_CLOSED_INTERVIEW_MSG, HttpStatus.NOT_ACCEPTABLE);
        }

        Type type = new TypeToken<List<Answer>>() { }.getType();
        List<Answer> answers = JSONParser.convertJsonStringToObject(data, type);

        User user = interview.isOpenType() ? getUserByPrincipal(principal) : null;
        if (user == null) {
            Cookie interviewHash = new Cookie(WebConstants.HASH, hash);
            Cookie isPassed = new Cookie(WebConstants.IS_PASSED, WebConstants.IS_PASSED_VAL);

            int maxAge = DateUtils.secondsBetweenDays(interview.getEndDate());

            interviewHash.setMaxAge(maxAge);
            isPassed.setMaxAge(maxAge);

            response.addCookie(interviewHash);
            response.addCookie(isPassed);
        }

        try {
            userAnswerService.save(user, interview, answers);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(WebConstants.USER_SEND_PASSED_INTERVIEW_MSG, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
