package by.gstu.interviewstreet.web.controller.user;

import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.web.SecurityConstants;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/respondent")
public class RespondentController extends UserController {

    @Autowired
    InterviewService interviewService;

    @Autowired
    UserAnswerService userAnswerService;

    @Autowired
    UserInterviewService userInterviewService;

    @Secured({SecurityConstants.EDITOR, SecurityConstants.RESPONDENT, SecurityConstants.ANONYMOUS})
    @RequestMapping("/{hash}/success")
    public String showSuccessPage(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);
        model.addAttribute(AttrConstants.SHOW_LINK, interview.isOpenType());

        return "success";
    }

    @Secured({SecurityConstants.EDITOR, SecurityConstants.RESPONDENT})
    @RequestMapping("/dashboard")
    public String showDashboard(Principal principal, Model model) {
        User user = getUserByPrincipal(principal);
        List<UserInterview> availableInterviews = WebUtils.getAvailableInterviews(user.getInterviewsForPassing());

        model.addAttribute(AttrConstants.USER_INTERVIEWS, availableInterviews);

        return "dashboard";
    }

    @Secured({SecurityConstants.EDITOR, SecurityConstants.RESPONDENT})
    @RequestMapping(value = "/{hash}/interview", method = RequestMethod.GET)
    public String showInterview(@PathVariable String hash, Principal principal, Model model) {
        UserInterview uInterview = userInterviewService.getByUserAndInterview(principal.getName(), hash);
        if (uInterview == null || (!uInterview.getInterview().isSecondPassage() && uInterview.getPassed())) {
            return "redirect:/dashboard";
        }

        Interview interview = uInterview.getInterview();
        if (interview.getHide() || interview.getIsDeadline()) {
            return "error/404";
        }

        WebUtils.buildInterviewModel(model, interview);

        return "interview";
    }

    @Secured({SecurityConstants.EDITOR, SecurityConstants.RESPONDENT, SecurityConstants.ANONYMOUS})
    @RequestMapping(value = {"interview/{hash}/anonymous", "interview/{hash}/expert"}, method = RequestMethod.GET)
    public String showAnonymousInterview(@PathVariable String hash, HttpServletRequest request, Model model) {
        Interview interview = interviewService.get(hash);
        if (interview.getHide() || interview.getIsDeadline()) {
            return "error/404";
        }

        /*if (WebUtils.isFilledCookie(request)) {
            return "error/403";
        }*/

        WebUtils.buildInterviewModel(model, interview);

        return "interview";
    }

    @ResponseBody
    @Secured({SecurityConstants.EDITOR, SecurityConstants.RESPONDENT, SecurityConstants.ANONYMOUS})
    @RequestMapping(value = "/send/interview", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> sendInterview(String hash, String data, Principal principal,
                                                @RequestParam(required = false) String firstname,
                                                @RequestParam(required = false) String lastname) {
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
        Map<String, Object> cookies = new HashMap<>();
        if (user == null) {
            int maxAge = DateUtils.secondsBetweenDays(interview.getEndDate());

            cookies.put(WebConstants.HASH, hash);
            cookies.put(WebConstants.MAX_AGE, maxAge);
            cookies.put(WebConstants.IS_PASSED, WebConstants.IS_PASSED_VAL);
        }

        try {
            userAnswerService.save(user, interview, answers);
            if (interview.isExpertType()) {
                interviewService.saveExpertInterview(new ExpertInterview(interview, firstname, lastname));
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(WebConstants.USER_SEND_PASSED_INTERVIEW_MSG, HttpStatus.BAD_REQUEST);
        }

        String jsonData = JSONParser.convertObjectToJsonString(cookies);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

}
