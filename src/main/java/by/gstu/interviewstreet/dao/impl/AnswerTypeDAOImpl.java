package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.AnswerTypeDAO;
import by.gstu.interviewstreet.domain.AnswerType;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerTypeDAOImpl extends GenericDAOImpl<AnswerType, Integer> implements AnswerTypeDAO {

    @Override
    public AnswerType getByName(String name) {
        return (AnswerType) currentSession()
                .createQuery("FROM AnswerType WHERE name LIKE :name")
                .setString("name", name)
                .uniqueResult();
    }

}
