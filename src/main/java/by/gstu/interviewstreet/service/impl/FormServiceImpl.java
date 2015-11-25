package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IFormDAO;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormServiceImpl implements FormService {

    @Autowired
    IFormDAO formDAO;

    @Override
    @Transactional
    public void save(List<Answer> answers, Question question) {
        formDAO.saveForm(answers, question);
    }
}
