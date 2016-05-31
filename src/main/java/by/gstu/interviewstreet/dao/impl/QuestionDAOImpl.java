package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.QuestionDAO;
import by.gstu.interviewstreet.domain.Question;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDAOImpl extends GenericDAOImpl<Question, Integer> implements QuestionDAO {

    @Override
    public Question getByNumber(int number) {
        return (Question) currentSession()
                .createQuery("FROM Question WHERE number = :number")
                .setInteger("number", number)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Question> getAllWhoseNumberMoreOrEquals(int number) {
        return currentSession()
                .createQuery("FROM Question WHERE number >= :number")
                .setInteger("number", number)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Question> getAllWhoseNumberMore(int number) {
        return currentSession()
                .createQuery("FROM Question WHERE number > :number")
                .setInteger("number", number)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Question> getAllOrderByNumber(String hash) {
        return currentSession()
                .createQuery("FROM Question WHERE interview.hash LIKE :hash ORDER BY number ASC")
                .setString("hash", hash)
                .list();
    }

    /*Used when add new question*/
    @Override
    @SuppressWarnings("unchecked")
    public void incrementNumbers(List<Question> questions) {
        Session session = currentSession();
        for (Question question : questions) {
            int curNumber = question.getNumber();
            question.setNumber(++curNumber);

            session.save(question);
        }
    }

    /*Used when remove question*/
    @Override
    @SuppressWarnings("unchecked")
    public void decrementNumbers(List<Question> questions) {
        Session session = currentSession();
        for (Question question : questions) {
            int curNumber = question.getNumber();
            question.setNumber(--curNumber);

            session.save(question);
        }
    }

}
