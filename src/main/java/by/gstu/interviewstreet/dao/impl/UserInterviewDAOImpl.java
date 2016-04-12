package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.UserInterviewDAO;
import by.gstu.interviewstreet.domain.UserInterview;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInterviewDAOImpl extends AbstractDbDAO implements UserInterviewDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<UserInterview> getByInterviewId(int interviewId) {
        return getSession()
                .createQuery("FROM UserInterview WHERE interview.id =:interviewId GROUP BY user.employee.post.id")
                .setInteger("interviewId", interviewId)
                .list();
    }

    @Override
    public void save(UserInterview userInterview) {
        getSession().save(userInterview);
    }

    @Override
    public void remove(UserInterview userInterview) {
        getSession().delete(userInterview);
    }

}
