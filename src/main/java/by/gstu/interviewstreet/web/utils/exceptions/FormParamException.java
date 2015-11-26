package by.gstu.interviewstreet.web.utils.exceptions;

public class FormParamException extends Exception{

    public FormParamException(String message) {
        super(message);
    }

    public FormParamException() {
    }

    public FormParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
