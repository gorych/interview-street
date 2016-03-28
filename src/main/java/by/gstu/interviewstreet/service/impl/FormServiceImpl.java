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

    private static final int BASE_FORM_COUNT = 1;
    private static final int COMPLEX_FORM_COUNT = 3;

    private static final String TEXT_TYPE = "text";
    private static final String RADIO_TYPE = "radio";
    private static final String RATING_TYPE = "rating";
    private static final String CHECKBOX_TYPE = "checkbox";

    private static final String DEFAULT_QUESTION_TEXT = "Введите текст вопроса";
    private static final String DEFAULT_ANSWER_TEXT = "Ответ";

    @Autowired
    IFormDAO formDAO;

    @Autowired
    IQuestionDAO questionDAO;

    @Autowired
    IAnswerDAO answerDAO;

    @Override
    @Transactional
    public List<Form> buildQuestionForm(Interview interview, AnswerType answerType) {
        int formCount = 0;
        List<Form> forms = new ArrayList<>();

        String name = answerType.getName();
        if (TEXT_TYPE.equals(name) || RATING_TYPE.equals(name)) {
            formCount = BASE_FORM_COUNT;
        }

        if (RADIO_TYPE.equals(name) || CHECKBOX_TYPE.equals(name)) {
            formCount = COMPLEX_FORM_COUNT;
        }

        /*Build default answer and question*/
        Answer answer = new Answer(DEFAULT_ANSWER_TEXT, answerType);
        Question question = new Question(DEFAULT_QUESTION_TEXT);
        Form form = new Form(answer, question, interview);

        /*Add to DB and get full entity with ID*/
        answer = answerDAO.insert(answer);
        question = questionDAO.insert(question);

        for (int i = 0; i < formCount; i++) {
            answer.setText(DEFAULT_ANSWER_TEXT + (i + 1));
            form.setAnswer(answer);

            formDAO.insert(form);
            forms.add(form);
        }

        return forms;
    }

    @Override
    @Transactional
    public void save(List<Answer> answers, Question question) {
        formDAO.saveForm(answers, question);
    }

    @Override
    @Transactional
    public void remove(Question question) {
        List<Form> forms = formDAO.getByQuestion(question);
        if (forms != null) {
            answerDAO.remove(forms);
            questionDAO.remove(question);
        }
    }

    @Override
    @Transactional
    public String getJSON(int questionId) {
        Question question = questionDAO.qetById(questionId);
        List<Form> forms = formDAO.getByQuestion(question);
        if (forms == null) {
            return "";
        }

        StringBuilder answerTexts = new StringBuilder();
        StringBuilder answerIds = new StringBuilder();
        for (Form form : forms) {
            String text = form.getAnswer().getText();
            int id = form.getAnswer().getId();

            answerTexts.append(text).append("\n");
            answerIds.append(id).append("\n");
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
