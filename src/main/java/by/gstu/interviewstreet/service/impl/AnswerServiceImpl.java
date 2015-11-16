package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.impl.AnswerDAOImpl;
import by.gstu.interviewstreet.dao.impl.AnswerTypeDAOImpl;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerServiceImpl implements AnswerService{

    @Autowired
    private AnswerDAOImpl answerDAO;

    @Autowired
    private AnswerTypeDAOImpl answerTypeDAO;

    @Override
    @Transactional
    public long insertAnswer() {
        AnswerType answerType = answerTypeDAO.getDefaultAnswerType();
        return answerDAO.insertAnswer(answerType);
    }
}
