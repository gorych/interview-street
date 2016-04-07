package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.AnswerDAO;
import by.gstu.interviewstreet.domain.Answer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerDAOImpl extends AbstractDbDAO implements AnswerDAO {

    @Override
    public Answer getById(int id) {
        return (Answer) getSession()
                .createQuery("FROM Answer WHERE id=:id")
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Answer> getByIds(List<Integer> ids) {
        return getSession()
                .createQuery("FROM Answer WHERE id IN (:ids)")
                .setParameterList("ids", ids)
                .list();
    }

    @Override
    public void saveOrUpdate(Answer answer) {
        getSession().save(answer);
    }

    @Override
    public void remove(Answer answer) {
        getSession().delete(answer);
    }
}
