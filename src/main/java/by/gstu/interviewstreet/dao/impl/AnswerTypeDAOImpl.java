package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.AnswerTypeDAO;
import by.gstu.interviewstreet.domain.AnswerType;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerTypeDAOImpl extends AbstractDbDAO implements AnswerTypeDAO {

    @Override
    public AnswerType getById(int id) {
        return (AnswerType) getSession()
                .createQuery("FROM AnswerType WHERE id = :id")
                .setInteger("id", id)
                .uniqueResult();
    }

    @Override
    public AnswerType getByName(String name) {
        return (AnswerType) getSession()
                .createQuery("FROM AnswerType WHERE name LIKE :name")
                .setString("name", name)
                .uniqueResult();
    }

}
