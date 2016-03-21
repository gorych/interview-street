package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IUserInterviewDAO;
import by.gstu.interviewstreet.domain.UserInterview;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInterviewDAOImpl extends AbstractDbDAO implements IUserInterviewDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<UserInterview> getById(int interviewId) {
        return getSession()
                .createQuery("FROM UserInterview WHERE interview.id =:interviewId GROUP BY user.employee.post.id")
                .setInteger("interviewId", interviewId)
                .list();
    }

    @Override
    public void save(UserInterview userInterview) {
        getSession().save(userInterview);
    }


}
