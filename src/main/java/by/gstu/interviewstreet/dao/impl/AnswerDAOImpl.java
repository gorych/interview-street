package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IAnswerDAO;
import by.gstu.interviewstreet.domain.Answer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerDAOImpl extends AbstractDbDAO implements IAnswerDAO {

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
    public void remove(int id) {
        Answer answer = (Answer) getSession().load(Answer.class, id);
        if (answer != null) {
            getSession().delete(answer);
        }
    }
}
