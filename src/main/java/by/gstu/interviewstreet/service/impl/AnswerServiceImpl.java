package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IAnswerDAO;
import by.gstu.interviewstreet.dao.IAnswerTypeDAO;
import by.gstu.interviewstreet.dao.IFormDAO;
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
    private IAnswerDAO answerDAO;

    @Autowired
    private IAnswerTypeDAO answerTypeDAO;

    @Autowired
    private IFormDAO formDAO;

    @Override
    @Transactional
    public long insert(Form form) {
        AnswerType answerType = answerTypeDAO.getDefaultAnswerType();
        Answer answer = answerDAO.insert(answerType);

        form.setAnswer(answer);
        formDAO.insertForm(form);

        return answer.getId();
    }

    @Override
    @Transactional
    public List<Answer> get(Integer[] ids) {
        return answerDAO.getByIds(ids);
    }

    @Override
    @Transactional
    public AnswerType getAnswerType(int id) {
        return answerTypeDAO.getAnswerTypeById(id);
    }

    @Override
    @Transactional
    public void remove(int id) {
        answerDAO.remove(id);
    }
}
