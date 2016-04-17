package by.gstu.interviewstreet.web.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CrashController {

    @RequestMapping(value = {"/403"}, method = RequestMethod.GET)
    public String accessDenied() {
        return "error/403";
    }

    @RequestMapping(value = {"/404"}, method = RequestMethod.GET)
    public String resourceNotFound() {
        return "error/404";
    }

}
