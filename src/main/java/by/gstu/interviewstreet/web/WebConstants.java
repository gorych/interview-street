package by.gstu.interviewstreet.web;

public final class WebConstants {

    public static final String ENCODING_PRODUCE = "text/plain; charset=UTF-8";

    public static final String HASH = "hash";
    public static final String SURVEY = "survey";
    public static final String MAX_AGE = "max_age";


    public static final String TEXT_ANSWER_NAME = "text";
    public static final int ANSWER_COUNT_WITH_TEXT_ANSWER = 3;
    public static final int ANSWER_COUNT_WITHOUT_TEXT_ANSWER = 2;


    public static final String USER_NOT_FOUND_MSG = "Пользователь с такими учетными данными не найден.";
    public static final String USER_SEND_WRONG_HASH_MSG = "User tries to send a interview with the wrong hash =";
    public static final String USER_SEND_CLOSED_INTERVIEW_MSG = "User tries to send a interview which was closed.";
    public static final String USER_SEND_PASSED_INTERVIEW_MSG = "User send a interview which already has passed.";


    private WebConstants() {
    }

}
