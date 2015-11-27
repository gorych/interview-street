package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IAnswerDAO;
import by.gstu.interviewstreet.dao.IFormDAO;
import by.gstu.interviewstreet.dao.IQuestionDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.FormService;
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
public class FormServiceImpl implements FormService {

    @Autowired
    IFormDAO formDAO;

    @Autowired
    IQuestionDAO questionDAO;

    @Autowired
    IAnswerDAO answerDAO;

    @Override
    @Transactional
    public void save(List<Answer> answers, Question question) {
        formDAO.saveForm(answers, question);
    }

    @Override
    @Transactional
    public void remove(Question question) {
        List<Form> forms = formDAO.getByQuestion(question);
        answerDAO.remove(forms);
        questionDAO.remove(question);
    }

    @Override
    @Transactional
    public String getJsonString(int questionId) {
        Question question = questionDAO.qetQuestionById(questionId);
        List<Form> forms = formDAO.getByQuestion(question);
        if (forms == null) {
            return "";
        }

        StringBuilder answerTexts = new StringBuilder();
        StringBuilder answerIds = new StringBuilder();
        for (Form form : forms) {
            String text = form.getAnswer().getText();
            int id = form.getAnswer().getId();

            answerTexts.append(text).append(",");
            answerIds.append(id).append(",");
        }
        answerTexts.deleteCharAt(answerTexts.length() - 1);
        answerIds.deleteCharAt(answerIds.length() - 1);

        List<Map<String, Object>> jsonList = new ArrayList<>();
        Map<String, Object> jsonObject = new HashMap<>();

        Form form = forms.get(0);
        jsonObject.put("type", form.getAnswer().getType().getId());
        jsonObject.put("question", form.getQuestion().getText());
        jsonObject.put("answers", answerTexts);
        jsonObject.put("answer_ids", answerIds);

        jsonList.add(jsonObject);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(jsonList);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
