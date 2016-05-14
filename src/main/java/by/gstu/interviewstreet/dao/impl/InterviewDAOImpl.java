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
    public List<Interview> getAllInRange(int from, int howMany, String userCredential) {
        return getSession()
                .createQuery("FROM Interview WHERE creator.passportData LIKE :userCredential ORDER BY placementDate DESC")
                .setString("userCredential", userCredential)
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
    public void saveOrUpdate(Interview interview) {
        getSession().saveOrUpdate(interview);
    }

    @Override
    public void saveExpertInterview(ExpertInterview expertInterview) {
        getSession().saveOrUpdate(expertInterview);
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
