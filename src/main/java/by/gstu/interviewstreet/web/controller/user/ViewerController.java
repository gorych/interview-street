package by.gstu.interviewstreet.web.controller.user;

import by.gstu.interviewstreet.security.UserRoleConstants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("/viewer")
@Secured(UserRoleConstants.VIEWER)
public class ViewerController {

    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    public String showStatistics() {
        return "statistics";
    }

}
