package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.InterviewDAO;
import by.gstu.interviewstreet.domain.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InterviewDAOImpl extends AbstractDbDAO implements InterviewDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Interview> getAll() {
        return getSession()
                .createQuery("FROM Interview")
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Interview> getAllInRange(int from, int howMany) {
        return getSession()
                .createQuery("FROM Interview ORDER BY placementDate DESC")
                .setFirstResult(from)
                .setMaxResults(howMany)
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
    public Interview getByHash(String hash) {
        return (Interview) getSession()
                .createQuery("FROM Interview WHERE hash LIKE :hash")
                .setString("hash", hash)
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

}
