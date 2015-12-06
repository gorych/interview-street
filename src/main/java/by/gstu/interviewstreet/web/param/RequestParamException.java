package by.gstu.interviewstreet.web.param;


public class RequestParamException extends Exception{

    public RequestParamException(String message) {
        super(message);
    }

    public RequestParamException() { }

    public RequestParamException(String message, Throwable cause) {
        super(message, cause);
    }
}