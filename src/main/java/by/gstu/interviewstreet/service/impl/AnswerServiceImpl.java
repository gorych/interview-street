package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IAnswerDAO;
import by.gstu.interviewstreet.dao.IAnswerTypeDAO;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
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

    @Override
    @Transactional
    public List<Answer> get(List<Integer> ids) {
        return answerDAO.getByIds(ids);
    }

    @Override
    @Transactional
    public AnswerType getAnswerType(int id) {
        return answerTypeDAO.getById(id);
    }

    @Override
    @Transactional
    public void remove(int id) {
        answerDAO.remove(id);
    }
}
