package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.AnswerDAO;
import by.gstu.interviewstreet.domain.Answer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerDAOImpl extends GenericDAOImpl<Answer, Integer> implements AnswerDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Answer> getByIds(List<Integer> ids) {
        return currentSession()
                .createQuery("FROM Answer WHERE id IN (:ids)")
                .setParameterList("ids", ids)
                .list();
    }

}
