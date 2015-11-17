package by.gstu.interviewstreet.web.controllers;

import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.service.UserService;
import by.gstu.interviewstreet.web.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    UserService userService;

    @ModelAttribute(AttributeConstants.USER_INITIALS)
    public String userFullName() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByPassportData(username);
        return user != null ? user.getEmployee().getInitials() : "anonymous";
    }

}
