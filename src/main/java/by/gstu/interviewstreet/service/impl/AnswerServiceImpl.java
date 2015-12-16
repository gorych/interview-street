package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.*;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.AnswerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private IAnswerDAO answerDAO;

    @Autowired
    private IQuestionDAO questionDAO;

    @Autowired
    private IAnswerTypeDAO answerTypeDAO;

    @Autowired
    private IInterviewDAO interviewDAO;

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
    public void insertUserAnswers(Interview interview, List<Integer> questionIds, Map<Integer, String[]> answers, User user) {
        List<Question> questions = questionDAO.qet(questionIds);

        Calendar calender = Calendar.getInstance();
        java.util.Date utilDate = calender.getTime();
        java.sql.Date currentDate = new java.sql.Date(utilDate.getTime());

        for (Question question : questions) {
            String[] userAnswers = answers.get(question.getId());
            for (String answer : userAnswers) {
                answerDAO.insertUserAnswer(new UserAnswer(user, question, interview, answer, currentDate));
            }
        }
        if (user != null) {
            interviewDAO.pass(interview.getId(), user.getId());
        }
    }

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
    public String getJSON(int questionId) {
        List<UserAnswer> answers = answerDAO.getUserAnswers(questionId);
        List<Integer> count = answerDAO.getAnswerCount(questionId);

        List<Map<String, String>> jsonList = new ArrayList<>();

        Map<String, String> jsonObject = new HashMap<>();
        StringBuilder res = new StringBuilder();
        StringBuilder counts = new StringBuilder();

        for (int i = 0; i < answers.size(); i++) {
            UserAnswer answer = answers.get(i);
            res.append(answer.getAnswer()).append(";");
            counts.append(count.get(i)).append(";");
        }

        jsonObject.put("answer", res.deleteCharAt(res.length() - 1).toString());
        jsonObject.put("counts", counts.deleteCharAt(counts.length() - 1).toString());
        jsonObject.put("allNumber", count.size() + "");
        jsonList.add(jsonObject);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(jsonList);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Override
    @Transactional
    public void remove(int id) {
        answerDAO.remove(id);
    }
}
