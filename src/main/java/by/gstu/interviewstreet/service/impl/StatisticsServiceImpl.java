package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.bean.StatisticData;
import by.gstu.interviewstreet.dao.UserAnswerDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.StatisticsService;
import by.gstu.interviewstreet.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    UserAnswerDAO userAnswerDAO;

    private int getMaxEstimate(Question question) {
        return question.isRateType()
                ? Integer.parseInt(question.getAnswers().get(0).getText())
                : 0;
    }

    private Map<String, Object[]> getAnswerDataMap(List<UserAnswer> allAnswers, List<UserAnswer> notDuplicateAnswers, int total) {
        Map<String, Object[]> answerData = new HashMap<>();

        for (UserAnswer userAnswer : notDuplicateAnswers) {
            int count = Collections.frequency(allAnswers, userAnswer);
            if (count > 0) {
                String percent = WebUtils.getPercent(count, total);
                answerData.put(userAnswer.getAnswerText(), new Object[]{count, percent});
            }
        }

        return answerData;
    }

    private List<UserAnswer> filterUserAnswers(List<UserAnswer> answers, Subdivision sub, PublishedInterview publish) {
        if (sub != null) {
            answers = answers.stream()
                    .filter(a -> a.getUser().getEmployee().getSubdivision().getId() == sub.getId())
                    .collect(Collectors.toList());
        }

        if (publish != null) {
            answers = answers.stream()
                    .filter(a -> a.getReplyDate().compareTo(publish.getPublishDate()) >= 0 &&
                            a.getReplyDate().compareTo(publish.getCloseDate()) <= 0)
                    .collect(Collectors.toList());
        }

        return answers;
    }

    @Override
    @Transactional
    public List<StatisticData> getInterviewStatistics(Interview interview, Subdivision sub, PublishedInterview publish) {
        List<Question> questions = interview.getSortedQuestions();

        List<StatisticData> statistics = new ArrayList<>();
        for (Question question : questions) {
            List<UserAnswer> allAnswers = question.getUserAnswers();
            List<UserAnswer> notDuplicateAnswers = userAnswerDAO.getAnswersByQuestion(question);

            allAnswers = filterUserAnswers(allAnswers, sub, publish);
            notDuplicateAnswers = filterUserAnswers(notDuplicateAnswers, sub, publish);

            String avg = null;
            if (question.isRateType()) {
                avg = WebUtils.getAvgEstimate(notDuplicateAnswers);
            }

            int total = allAnswers.size();
            int maxEstimate = getMaxEstimate(question);
            Map<String, Object[]> answerData = getAnswerDataMap(allAnswers, notDuplicateAnswers, total);

            if (!answerData.isEmpty()) {
                statistics.add(new StatisticData(question, answerData, maxEstimate, total, avg));
            }
        }

        return statistics;
    }

}
