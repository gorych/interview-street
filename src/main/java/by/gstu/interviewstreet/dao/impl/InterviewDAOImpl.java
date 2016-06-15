package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.InterviewDAO;
import by.gstu.interviewstreet.domain.ExpertInterview;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.PublishedInterview;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.util.DateUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

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
    public void lockOrUnlock(int interviewId) {
        Session session = currentSession();

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
            interview = find(interviewId);
        }

        PublishedInterview publishedInterview;
        if (interview.getHide()) {
            interview.setHide(false);
            publishedInterview = new PublishedInterview(interview);
        } else {
            interview.setHide(true);
            publishedInterview = getPublishedById(interview.getId());
            publishedInterview.setCloseDate(DateUtils.getToday());
        }

        session.save(publishedInterview);
        session.save(interview);
    }

}
