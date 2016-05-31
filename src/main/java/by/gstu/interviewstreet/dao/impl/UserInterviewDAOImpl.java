package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.UserInterviewDAO;
import by.gstu.interviewstreet.domain.UserInterview;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInterviewDAOImpl extends GenericDAOImpl<UserInterview, Integer> implements UserInterviewDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<UserInterview> getByInterviewAndGroupByPost(int interviewId) {
        return currentSession()
                .createQuery("FROM UserInterview WHERE interview.id =:interviewId GROUP BY user.employee.post.id")
                .setInteger("interviewId", interviewId)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserInterview> getByInterviewAndGroupBySubdivision(String hash) {
        return currentSession()
                .createQuery("FROM UserInterview WHERE interview.hash =:hash GROUP BY user.employee.subdivision")
                .setString("hash", hash)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserInterview> getByInterviewHash(String hash) {
        return currentSession()
                .createQuery("FROM UserInterview WHERE interview.hash =:hash")
                .setString("hash", hash)
                .list();
    }

    @Override
    public UserInterview getByUserAndInterview(String username, String hash) {
        return (UserInterview) currentSession()
                .createQuery("FROM UserInterview WHERE user.passportData =:username AND interview.hash =:hash")
                .setString("username", username)
                .setString("hash", hash)
                .uniqueResult();
    }

}
