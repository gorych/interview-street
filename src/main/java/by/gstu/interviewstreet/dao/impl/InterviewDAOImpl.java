package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.InterviewDAO;
import by.gstu.interviewstreet.domain.ExpertInterview;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.PublishedInterview;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.web.util.DateUtils;
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
    public List<PublishedInterview> getPublishedInterviews(Interview interview) {
        return getSession()
                .createQuery("FROM PublishedInterview WHERE interview.id=:id")
                .setInteger("id", interview.getId())
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
    public PublishedInterview getPublishById(int id) {
        return (PublishedInterview) getSession()
                .createQuery("FROM PublishedInterview WHERE id = :id")
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

        PublishedInterview publishedInterview;
        if (interview.getHide()) {
            interview.setHide(false);
            publishedInterview = new PublishedInterview(interview);
        } else {
            interview.setHide(true);
            publishedInterview = getPublishById(interview.getId());
            publishedInterview.setCloseDate(DateUtils.getToday());
        }

        session.save(publishedInterview);
        session.save(interview);
    }

}
