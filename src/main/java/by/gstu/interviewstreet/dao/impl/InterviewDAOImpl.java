package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IInterviewDAO;
import by.gstu.interviewstreet.domain.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InterviewDAOImpl extends AbstractDbDAO implements IInterviewDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Interview> getAll() {

        return getSession()
                .createQuery("FROM Interview")
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserInterview> getUserInterviews(User user) {
        return getSession()
                .createQuery("FROM UserInterview WHERE user.id = :id AND interview.hide = false AND isPassed != true " +
                        "AND interview.type.id = :typeId")
                .setInteger("id", user.getId())
                .setInteger("typeId", 1)
                .list();
    }

    @Override
    public Interview getById(int id) {
        return (Interview) getSession()
                .createQuery("FROM Interview WHERE id = :id")
                .setInteger("id", id)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Interview> getByType(int typeId) {
        return getSession()
                .createQuery("FROM Interview WHERE type.id = :id")
                .setInteger("id", typeId)
                .list();
    }

    @Override
    public Interview getByHash(long hash) {
        return (Interview) getSession()
                .createQuery("FROM Interview WHERE hash = :hash")
                .setLong("hash", hash)
                .uniqueResult();
    }

    @Override
    public void save(Interview interview) {
        getSession().save(interview);
    }

    @Override
    public void remove(Interview interview) {
        getSession().delete(interview);
    }

    @Override
    public void lockOrUnlock(int interviewId) {
        Session session = getSession();

        UserInterview userInterview = (UserInterview) session
                .createQuery("FROM UserInterview WHERE interview.id = :id GROUP BY interview.id")
                .setInteger("id", interviewId)
                .uniqueResult();

        Interview interview;
        if (userInterview != null) {
            interview = userInterview.getInterview();
            userInterview.setPassed(false);
            session.save(userInterview);
        } else {
            interview = getById(interviewId);
        }

        if (interview.getHide()) {
            interview.setHide(false);
        } else {
            interview.setHide(true);
        }

        session.save(interview);
    }

    @Override
    public void pass(int interviewId, int userId) {
        UserInterview interview = (UserInterview) getSession()
                .createQuery("FROM UserInterview WHERE interview.id = :id AND user.id =:userId")
                .setInteger("id", interviewId)
                .setInteger("userId", userId)
                .uniqueResult();
        interview.setPassed(true);
    }
}
