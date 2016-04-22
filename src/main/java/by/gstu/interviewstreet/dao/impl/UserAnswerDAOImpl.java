package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.UserAnswerDAO;
import by.gstu.interviewstreet.domain.UserAnswer;
import org.springframework.stereotype.Repository;

@Repository
public class UserAnswerDAOImpl extends AbstractDbDAO implements UserAnswerDAO {

    @Override
    public void save(UserAnswer userAnswer) {
        getSession().save(userAnswer);
    }
}
