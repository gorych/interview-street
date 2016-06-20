package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.InterviewDAO;
import by.gstu.interviewstreet.domain.ExpertInterview;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.PublishedInterview;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.util.DateUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import java.util.List;

@Repository
public class InterviewDAOImpl extends GenericDAOImpl<Interview, Integer> implements InterviewDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<PublishedInterview> getPublishedInterviews(Interview interview) {
        return currentSession()
                .createQuery("FROM PublishedInterview WHERE interview.id=:id")
                .setInteger("id", interview.getId())
                .list();
    }

    @Override
    public PublishedInterview getPublishedById(int id) {
        return (PublishedInterview) currentSession()
                .createQuery("FROM PublishedInterview WHERE id = :id")
                .setInteger("id", id)
                .uniqueResult();
    }

    @Override
    public Interview getByHash(String hash) {
        return (Interview) currentSession()
                .createQuery("FROM Interview WHERE hash LIKE :hash")
                .setString("hash", hash)
                .uniqueResult();
    }

    @Override
    public void saveExpertInterview(ExpertInterview expertInterview) {
        currentSession().save(expertInterview);
    }

    @Override
    public Interview lockOrUnlock(int interviewId) {
        Session session = currentSession();

        UserInterview creatorInterview = (UserInterview) session
                .createQuery("FROM UserInterview WHERE interview.id = :id GROUP BY interview.id")
                .setInteger("id", interviewId)
                .uniqueResult();

        Interview interview;
        if (creatorInterview != null) {
            interview = creatorInterview.getInterview();
            creatorInterview.setPassed(false);
            session.save(creatorInterview);
        } else {
            interview = find(interviewId);
        }

        if (interview.getHide()) {
            interview.setHide(false);
            session.save(new PublishedInterview(interview));
        } else {
            /*Generate new hash for interview*/
            byte[] bytes = (interview.getHash() + System.currentTimeMillis()).getBytes();

            interview.setHash(DigestUtils.md5DigestAsHex(bytes));
            interview.setHide(true);

            List<PublishedInterview> publishes = getPublishedByInterviewId(interview.getId());
            for (PublishedInterview publish : publishes) {
                publish.setCloseDate(DateUtils.getToday());
                session.save(publish);
            }
        }
        session.save(interview);

        return interview;
    }

    @SuppressWarnings("unchecked")
    private List<PublishedInterview> getPublishedByInterviewId(int id) {
        return currentSession()
                .createQuery("FROM PublishedInterview WHERE interview.id = :id")
                .setInteger("id", id)
                .list();
    }

}
