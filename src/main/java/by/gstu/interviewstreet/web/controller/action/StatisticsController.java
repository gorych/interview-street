package by.gstu.interviewstreet.web.controller.action;

import by.gstu.interviewstreet.security.UserRoleConstants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

@Controller("/statistics")
@Secured({UserRoleConstants.VIEWER, UserRoleConstants.EDITOR})
public class StatisticsController {


}
