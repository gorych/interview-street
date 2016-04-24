package by.gstu.interviewstreet.web;

public final class WebConstants {

    public static final String HASH = "hash";


    public static final String USER_NOT_FOUND_MSG = "Пользователь с такими паспортными данными не найден.";
    public static final String USER_SEND_WRONG_HASH_MSG = "User tries to send a interview with the wrong hash =";
    public static final String USER_SEND_CLOSED_INTERVIEW_MSG = "User tries to send a interview which was closed.";
    public static final String USER_SEND_PASSED_INTERVIEW_MSG = "User send a interview which already has passed.";


    private WebConstants() {
    }

}
