package by.gstu.interviewstreet.web.util;

import by.gstu.interviewstreet.domain.Question;

import java.util.Set;

public final class ControllerUtils {

    public static final String TEXT_ANSWER_NAME = "text";

    private ControllerUtils() {
    }

    public static boolean notExistTextAnswer(Set<Question> questions) {
        return questions.stream()
                .filter(quest -> quest.getType().getName().equals(TEXT_ANSWER_NAME)).count() < 1;
    }

}
