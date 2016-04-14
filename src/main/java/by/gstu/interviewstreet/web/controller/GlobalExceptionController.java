package by.gstu.interviewstreet.web.controller;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundException(NoHandlerFoundException e) {
        return "404";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String badRequestHandle(NoHandlerFoundException e) {
        return "404";
    }

}
