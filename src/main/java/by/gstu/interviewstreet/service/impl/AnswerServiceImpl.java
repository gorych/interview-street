package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.impl.AnswerDAOImpl;
import by.gstu.interviewstreet.dao.impl.AnswerTypeDAOImpl;
import by.gstu.interviewstreet.dao.impl.FormDAOImpl;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDAOImpl answerDAO;

    @Autowired
    private AnswerTypeDAOImpl answerTypeDAO;

    @Autowired
    private FormDAOImpl formDAO;

    @Override
    @Transactional
    public long insertAnswer(Form form) {
        AnswerType answerType = answerTypeDAO.getDefaultAnswerType();
        Answer answer = answerDAO.insertAnswer(answerType);

        form.setAnswer(answer);
        formDAO.insertForm(form);

        return answer.getId();
    }

    @Override
    @Transactional
    public List<Answer> getAnswersByIds(Integer[] ids) {
        return answerDAO.getAnswersByIds(ids);
    }

    @Override
    @Transactional
    public List<AnswerType> getAnswerTypesByIds(Integer[] ids) {
        return answerTypeDAO.getAnswerTypesByIds(ids);
    }
}
