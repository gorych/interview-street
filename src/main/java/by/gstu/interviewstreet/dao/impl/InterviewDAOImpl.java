package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IInterviewDAO;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserInterview;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InterviewDAOImpl extends AbstractDbDAO implements IInterviewDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Interview> getAll() {
        return getSession()
                .createQuery("FROM Interview ORDER BY placementDate DESC")
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserInterview> getUserInterviews(User user) {
        return getSession()
                .createQuery("FROM UserInterview WHERE user.id = :id AND interview.hide = false AND isPassed != true " +
                        "AND interview.type.id = :interviewId")
                .setInteger("id", user.getId())
                .setInteger("interviewId", 1)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Form> getInterviewQuestions(int interviewId) {
        return getSession().createQuery("FROM Form WHERE interview.id = :id GROUP BY question.id")
                .setInteger("id", interviewId)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Form> getInterviewQuestions(long hash) {
        return getSession()
                .createQuery("FROM Form WHERE interview.hash = :hash GROUP BY question.id")
                .setLong("hash", hash)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<List<Form>> getInterviewAnswers(List<Form> questionForm) {
        List<List<Form>> answers = new ArrayList<>();
        Query query = getSession().createQuery("FROM Form WHERE question.id = :id");
        for (Form form : questionForm) {
            query.setInteger("id", form.getQuestion().getId());
            answers.add(query.list());
        }
        return answers;
    }


    @Override
    public Interview getById(int id) {
        return (Interview) getSession()
                .createQuery("FROM Interview WHERE id = :id")
                .setInteger("id", id)
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Interview> getByType(int typeId) {
        return getSession()
                .createQuery("FROM Interview WHERE type.id = :id")
                .setInteger("id", typeId)
                .list();
    }

    @Override
    public Interview getByHash(long hash) {
        return (Interview) getSession()
                .createQuery("FROM Interview WHERE hash = :hash")
                .setLong("hash", hash)
                .uniqueResult();
    }

    @Override
    public int insert(Interview interview) {
        Serializable result = getSession().save(interview);
        if (result != null) {
            return (Integer) result;
        }
        return -1;
    }

    @Override
    public void insert(Interview interview, List<User> users) {
        int id = insert(interview);
        interview.setId(id);
        for (User user : users) {
            getSession().save(new UserInterview(interview, user));
        }
    }

    @Override
    public void remove(List<Integer> interviewIds) {
        for (int id : interviewIds) {
            Interview interview = (Interview) getSession().load(Interview.class, id);
            if (interview != null) {
                getSession().delete(interview);
            }
        }
    }

    @Override
    public void hide(int interviewId) {
        UserInterview userInterview = (UserInterview) getSession()
                .createQuery("FROM UserInterview WHERE interview.id = :id GROUP BY interview.id")
                .setInteger("id", interviewId)
                .uniqueResult();

        if (userInterview != null) {
            Interview interview = userInterview.getInterview();
            boolean hidden = !interview.isHide();
            if (!hidden) {
                userInterview.setIsPassed(false);
            }
            interview.setHide(hidden);
            getSession().save(interview);
        }
    }

    @Override
    public void pass(int interviewId, int userId) {
        UserInterview interview = (UserInterview) getSession()
                .createQuery("FROM UserInterview WHERE interview.id = :id AND user.id =:userId")
                .setInteger("id", interviewId)
                .setInteger("userId", userId)
                .uniqueResult();
        interview.setIsPassed(true);
    }
}
