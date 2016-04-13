package by.gstu.interviewstreet.web.util;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;
import java.util.Set;

public final class ControllerUtils {

    public static final String TEXT_ANSWER_NAME = "text";

    private ControllerUtils() {
    }

    public static boolean notExistTextAnswer(Set<Question> questions) {
        return questions.stream()
                .filter(quest -> quest.getType().getName().equals(TEXT_ANSWER_NAME)).count() < 1;
    }

    public static List<Interview> sortInterviewList(List<Interview> interviews) {
        interviews.sort((o1, o2) -> o1.getPlacementDate().compareTo(o2.getPlacementDate()));
        return interviews;
    }

    public static int getPageCount(int allInterviewSize, double cardsOnPage) {
        return (int) Math.ceil(allInterviewSize / cardsOnPage);
    }

}
