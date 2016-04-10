package by.gstu.interviewstreet.web.controller;


import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.UserService;
import by.gstu.interviewstreet.web.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/user")
@Secured(UserRoleConstants.EDITOR)
public class UserController {

    @Autowired
    public UserService userService;

    @ModelAttribute(AttributeConstants.USER_INITIALS)
    public String addUserInitials(Principal principal) {
        String username = principal.getName();
        User user = userService.get(username);

        return user.getEmployee().getInitials();
    }

    @ResponseBody
    @RequestMapping(value = {"/hide-chip"}, method = RequestMethod.GET)
    public ResponseEntity<String> hideChip(HttpSession session) {
        session.setAttribute(AttributeConstants.CHIP, false);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
