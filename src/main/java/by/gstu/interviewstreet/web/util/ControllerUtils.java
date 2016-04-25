package by.gstu.interviewstreet.web.util;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.UserInterview;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ControllerUtils {

    public static final String TEXT_ANSWER_NAME = "text";

    private ControllerUtils() {
    }

    public static List<UserInterview> getAvailableInterviews(List<UserInterview> interviews) {
        return interviews
                .stream()
                .filter(ui -> !ui.getInterview().getHide() && !ui.getPassed() && !ui.getInterview().getIsDeadline())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean notExistTextAnswer(List<Answer> answers) {
        return answers.stream()
                .filter(a -> a.getType().getName().equals(TEXT_ANSWER_NAME)).count() < 1;
    }

    public static int getPageCount(int allInterviewSize, double cardsOnPage) {
        return (int) Math.ceil(allInterviewSize / cardsOnPage);
    }

    public static String getPercent(int count, double total) {
        return new DecimalFormat("#0.0").format(count * 100 / total);
    }

    /*Used for building pagination*/
    public static int[] getPaginationBounds(int pageNumber, int pageCount, int cardCountPerPage, int startPageNumber) {
        int leftBorder = startPageNumber;
        int rightBorder = cardCountPerPage;

        while (true) {
            if (pageNumber == leftBorder) {
                rightBorder = leftBorder + cardCountPerPage - 1;
                if (rightBorder > pageCount) {
                    rightBorder = pageCount;
                }
                break;
            }

            if (pageNumber > leftBorder && pageNumber < rightBorder) {
                if (rightBorder > pageCount) {
                    rightBorder = pageCount;
                }
                break;
            }

            if (pageNumber == rightBorder) {
                leftBorder = pageNumber;
                rightBorder = leftBorder + cardCountPerPage - 1;
                if (rightBorder >= pageCount) {
                    leftBorder = pageNumber - 3;
                    rightBorder = pageCount;
                }
                break;
            }

            leftBorder = rightBorder;
            rightBorder += cardCountPerPage - 1;
        }

        return new int[]{leftBorder, rightBorder};
    }

}
