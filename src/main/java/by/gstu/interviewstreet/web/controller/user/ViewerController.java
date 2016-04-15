package by.gstu.interviewstreet.web.controller.user;

import by.gstu.interviewstreet.security.UserRoleConstants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

@Controller
@Secured(UserRoleConstants.VIEWER)
public class ViewerController {

    //TODO

}
