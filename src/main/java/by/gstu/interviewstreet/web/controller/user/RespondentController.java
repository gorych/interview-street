package by.gstu.interviewstreet.web.controller.user;

import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.service.UserAnswerService;
import by.gstu.interviewstreet.service.UserInterviewService;
import by.gstu.interviewstreet.util.DateUtils;
import by.gstu.interviewstreet.util.JSONParser;
import by.gstu.interviewstreet.util.WebUtils;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.SecurityConstants;
import by.gstu.interviewstreet.web.WebConstants;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static by.gstu.interviewstreet.web.WebConstants.ENCODING_PRODUCE;

@Controller
@RequestMapping("/respondent")
public class RespondentController extends UserController {
    private static final Logger LOG = LoggerFactory.getLogger(RespondentController.class);

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private UserInterviewService userInterviewService;

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
            LOG.warn("User try open closed or passed interview. Hash is" + hash);
            return "redirect:/dashboard";
        }

        Interview interview = uInterview.getInterview();
        if (interview.getHide() || interview.getIsDeadline()) {
            LOG.warn("Interview is hidden or elapsed time to survey. Hash is " + hash);
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
            LOG.warn("Interview is hidden or elapsed time to survey. Hash is " + hash);
            return "error/404";
        }

        WebUtils.buildInterviewModel(model, interview);

        return "interview";
    }

    @ResponseBody
    @Secured({SecurityConstants.EDITOR, SecurityConstants.RESPONDENT, SecurityConstants.ANONYMOUS})
    @RequestMapping(value = "/send/interview", method = RequestMethod.POST, produces = ENCODING_PRODUCE)
    public ResponseEntity<String> sendInterview(String hash, String data, Principal principal,
                                                @RequestParam(required = false) String firstname,
                                                @RequestParam(required = false) String lastname) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            LOG.warn("User try send nonexistent interview. Hash is " + hash);
            return new ResponseEntity<>(WebConstants.USER_SEND_WRONG_HASH_MSG + hash, HttpStatus.BAD_REQUEST);
        }

        if (interview.getHide()) {
            LOG.warn("User try send hidden interview. Hash is " + hash);
            return new ResponseEntity<>(WebConstants.USER_SEND_CLOSED_INTERVIEW_MSG, HttpStatus.NOT_ACCEPTABLE);
        }

        Type type = new TypeToken<List<Answer>>() { }.getType();
        List<Answer> answers = JSONParser.convertJsonStringToObject(data, type);

        /*If interview type is open, then data about user will not save*/
        User user = interview.isOpenType() ? getUserByPrincipal(principal) : null;
        Map<String, Object> cookies = new HashMap<>();
        if (user == null) {
            int maxAge = DateUtils.secondsBetweenDays(interview.getEndDate());

            cookies.put(WebConstants.HASH, hash);
            cookies.put(WebConstants.MAX_AGE, maxAge);
            cookies.put(WebConstants.IS_PASSED, WebConstants.IS_PASSED_VAL);
        }

        try {
            /*If interview type not expert when link to expert is null*/
            ExpertInterview expert = null;
            if (interview.isExpertType()) {
                expert = new ExpertInterview(interview, firstname, lastname);
                interviewService.saveExpertInterview(expert);
            }
            userAnswerService.save(expert, user, interview, answers);
        } catch (IllegalArgumentException e) {
            LOG.warn(e.getMessage());
            return new ResponseEntity<>(WebConstants.USER_SEND_PASSED_INTERVIEW_MSG, HttpStatus.BAD_REQUEST);
        }

        String jsonData = JSONParser.convertObjectToJsonString(cookies);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

}
