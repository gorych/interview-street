package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.QuestionDAO;
import by.gstu.interviewstreet.domain.Question;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDAOImpl extends AbstractDbDAO implements QuestionDAO {

    @Override
    public Question getById(int id) {
        return (Question) getSession()
                .createQuery("FROM Question WHERE id = :id")
                .setInteger("id", id)
                .uniqueResult();
    }

    @Override
    public Question getByNumber(int number) {
        return (Question) getSession()
                .createQuery("FROM Question WHERE number = :number")
                .setInteger("number", number)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Question> getAllWhoseNumberMoreOrEquals(int number) {
        return getSession()
                .createQuery("FROM Question WHERE number >= :number")
                .setInteger("number", number)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Question> getAllWhoseNumberMore(int number) {
        return getSession()
                .createQuery("FROM Question WHERE number > :number")
                .setInteger("number", number)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Question> getAllOrderByNumber(String hash) {
        return getSession()
                .createQuery("FROM Question WHERE interview.hash LIKE :hash ORDER BY number ASC")
                .setString("hash", hash)
                .list();
    }

    /*Used when add new question*/
    @Override
    @SuppressWarnings("unchecked")
    public void incrementNumbers(List<Question> questions) {
        Session session = getSession();
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
        Session session = getSession();
        for (Question question : questions) {
            int curNumber = question.getNumber();
            question.setNumber(--curNumber);

            session.save(question);
        }
    }

    @Override
    public void saveOrUpdate(Question question) {
        getSession().saveOrUpdate(question);
    }

    @Override
    public void remove(Question question) {
        getSession().delete(question);
    }

}
