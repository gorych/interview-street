package by.gstu.interviewstreet.web.controller;


import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.UserService;
import by.gstu.interviewstreet.web.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@Secured({UserRoleConstants.EDITOR})
public class UserController {

    @Autowired
    public UserService userService;

    @ModelAttribute(AttributeConstants.USER_INITIALS)
    public String addUserInitials(Principal principal) {
        String username = principal.getName();
        User user = userService.get(username);

        return user.getEmployee().getInitials();
    }

}
