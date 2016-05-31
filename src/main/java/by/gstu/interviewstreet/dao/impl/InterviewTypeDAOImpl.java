package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.InterviewTypeDAO;
import by.gstu.interviewstreet.domain.InterviewType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class InterviewTypeDAOImpl extends GenericDAOImpl<InterviewType, Integer> implements InterviewTypeDAO {

    @Override
    @Transactional
    public InterviewType getByName(String name) {
        return (InterviewType) currentSession()
                .createQuery("FROM InterviewType WHERE name LIKE :name")
                .setString("name", name)
                .uniqueResult();
    }
}
