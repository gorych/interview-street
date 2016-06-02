package by.gstu.interviewstreet.web.util;

import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.WebConstants;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WebUtils {

    public static final String TEXT_ANSWER_NAME = "text";
    public static final String FORMAT_TEMPL = "#0.0";

    private WebUtils() {
    }

    /*public static boolean isFilledCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (WebConstants.IS_PASSED.equals(cookie.getName())) {
                return true;
            }
        }
        return false;
    }*/

    public static void buildInterviewModel(Model model, Interview interview) {
        List<Question> questions = interview.getQuestions();

        Collections.sort(questions);

        model.addAttribute(AttrConstants.INTERVIEW, interview);
        model.addAttribute(AttrConstants.QUESTIONS, questions);
    }

    public static List<UserInterview> getAvailableInterviews(List<UserInterview> interviews) {
        List<UserInterview> availableInterviews = new ArrayList<>();

        for (UserInterview uInterview : interviews) {
            Interview interview = uInterview.getInterview();
            if (interview.getHide() || interview.getIsDeadline()) {
                continue;
            }

            if (!interview.isSecondPassage() && uInterview.getPassed()) {
                continue;
            }

            availableInterviews.add(uInterview);
        }

        return availableInterviews;
    }

    public static boolean notExistTextAnswer(List<Answer> answers) {
        return answers.stream()
                .filter(a -> a.getType().getName().equals(TEXT_ANSWER_NAME)).count() < 1;
    }

    public static int getPageCount(int allInterviewSize, double cardsOnPage) {
        return (int) Math.ceil(allInterviewSize / cardsOnPage);
    }

    public static String getPercent(int count, double total) {
        return new DecimalFormat(FORMAT_TEMPL).format(count * 100 / total);
    }

    public static String getAvgEstimate(List<UserAnswer> answers) {
        double sum = answers.stream().mapToInt(a -> Integer.parseInt(a.getAnswerText())).sum();
        return new DecimalFormat(FORMAT_TEMPL).format(sum / answers.size());
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
