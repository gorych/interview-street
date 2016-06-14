package by.gstu.interviewstreet.web.controller;


import by.gstu.interviewstreet.domain.UserRole;
import by.gstu.interviewstreet.service.MailService;
import by.gstu.interviewstreet.web.SecurityConstants;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collection;

@Controller
public class IndexController {

    @RequestMapping(value = {"/gateway"}, method = RequestMethod.GET)
    public String gateway() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Collection roles = authentication.getAuthorities();

        for (Object role : roles) {
            UserRole userRole = (UserRole) role;
            if (SecurityConstants.EDITOR.equals(userRole.getName())) {
                return "redirect:/editor/interview-list";
            }
        }

        return "redirect:/respondent/dashboard";
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String goToLogin(
            @RequestParam(value = "auth_error", required = false) String error, Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/gateway";
        }

        if (error != null) {
            model.addAttribute(AttrConstants.AUTH_ERROR, WebConstants.USER_NOT_FOUND_MSG);
        }

        return "login";
    }

}
