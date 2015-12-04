package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IInterviewDAO;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InterviewDAOImpl implements IInterviewDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Interview> getAllInterviews() {
        return sessionFactory.getCurrentSession().createQuery("FROM Interview")
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserInterview> getUserInterviews(User user) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM UserInterview WHERE user.id = :id AND interview.hide = false");
        query.setInteger("id",user.getId());

        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Form> getInterviewQuestions(int interviewId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Form WHERE interview.id = :id GROUP BY question.id");
        query.setInteger("id", interviewId);

        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<List<Form>> getInterviewAnswers(List<Form> questionForm) {
        List<List<Form>> answers = new ArrayList<>();

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Form WHERE question.id = :id");
        for (Form form : questionForm) {
            query.setInteger("id", form.getQuestion().getId());
            answers.add(query.list());
        }

        return answers;
    }


    @Override
    public Interview getInterviewById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Interview WHERE id = :id");
        query.setInteger("id", id);

        return (Interview) query.uniqueResult();
    }

    @Override
    public int insertInterview(Interview interview) {
        Serializable result = sessionFactory.getCurrentSession().save(interview);
        if (result != null) {
            return (Integer) result;
        }
        return -1;
    }

    @Override
    public void insertInterview(Interview interview, List<User> users) {
        int id = insertInterview(interview);
        interview.setId(id);

        Session session = sessionFactory.getCurrentSession();
        for (User user : users) {
            session.save(new UserInterview(interview, user));
        }
    }

    @Override
    public void removeInterviews(List<Integer> interviewIds) {
        Session session = sessionFactory.getCurrentSession();
        for (int id : interviewIds) {
            Interview interview = (Interview) session.load(Interview.class, id);
            if (interview != null) {
                session.delete(interview);
            }
        }
    }

    @Override
    public void hideInterview(int interviewId) {
        Session session = sessionFactory.getCurrentSession();
        Interview interview = (Interview) session.load(Interview.class, interviewId);
        if (interview != null) {
            boolean hidden = !interview.isHide();
            interview.setHide(hidden);
            session.save(interview);
        }
    }
}
