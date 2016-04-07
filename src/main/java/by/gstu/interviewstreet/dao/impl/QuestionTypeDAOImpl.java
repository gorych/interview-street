package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.QuestionTypeDAO;
import by.gstu.interviewstreet.domain.QuestionType;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionTypeDAOImpl extends AbstractDbDAO implements QuestionTypeDAO {


    @Override
    public QuestionType getById(int typeId) {
        return (QuestionType) getSession()
                .createQuery("FROM QuestionType WHERE id=:id")
                .setInteger("id", typeId)
                .uniqueResult();
    }
}
