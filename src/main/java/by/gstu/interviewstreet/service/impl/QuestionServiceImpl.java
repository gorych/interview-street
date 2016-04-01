package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IQuestionDAO;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private IQuestionDAO questionDAO;

    @Override
    @Transactional
    public Question get(int id) {
        return questionDAO.qetById(id);
    }
}
