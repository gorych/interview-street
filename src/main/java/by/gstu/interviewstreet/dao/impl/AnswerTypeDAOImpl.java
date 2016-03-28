package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IAnswerTypeDAO;
import by.gstu.interviewstreet.domain.AnswerType;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerTypeDAOImpl extends AbstractDbDAO implements IAnswerTypeDAO {

    @Override
    public AnswerType getById(int id) {
        return (AnswerType)getSession()
                .createQuery("FROM AnswerType WHERE id = :id")
                .setInteger("id", id)
                .uniqueResult();
    }
}
