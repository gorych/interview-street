package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IQuestionDAO;
import by.gstu.interviewstreet.domain.Form;
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

    @Override
    public String getJSON(List<Form> questionForms) {
        List<Map<String, String>> jsonList = new ArrayList<>();

        for (Form form : questionForms) {
            Question question = form.getQuestion();

            Map<String, String> jsonObject = new HashMap<>();
            jsonObject.put("id",question.getId() + "");
            jsonObject.put("text", question.getText());
            jsonList.add(jsonObject);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(jsonList);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
