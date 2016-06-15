package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.UserAnswerDAO;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.domain.UserAnswer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAnswerDAOImpl extends GenericDAOImpl<UserAnswer, Integer> implements UserAnswerDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAnswer> getAnswersByQuestion(Question question) {
        return currentSession()
                .createQuery("FROM UserAnswer WHERE question =:question GROUP BY answerText")
                .setEntity("question", question)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAnswer> getAnswersByInterviewHashAndText(Interview interview, String text) {
        final String SQL_STATEMENT =
                "FROM UserAnswer WHERE interview.hash LIKE :hash AND answerText LIKE :text GROUP BY "
                + (interview.isExpertType() ? "expert.id" : "user.id");
        return currentSession()
                .createQuery(SQL_STATEMENT)
                .setString("hash", interview.getHash())
                .setString("text", text)
                .list();
    }
}
