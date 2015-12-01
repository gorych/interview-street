package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IAnswerDAO;
import by.gstu.interviewstreet.dao.IAnswerTypeDAO;
import by.gstu.interviewstreet.dao.IFormDAO;
import by.gstu.interviewstreet.dao.IQuestionDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private IAnswerDAO answerDAO;

    @Autowired
    private IQuestionDAO questionDAO;

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
    public void insertUserAnswers(Interview interview, List<Integer> questionIds, Map<Integer, String[]> answers, User user) {
        List<Question> questions = questionDAO.qetQuestions(questionIds);

        Calendar calender = Calendar.getInstance();
        java.util.Date utilDate = calender.getTime();
        java.sql.Date currentDate = new java.sql.Date(utilDate.getTime());

        for (Question question : questions) {
            String[] userAnswers = answers.get(question.getId());
            for (String answer : userAnswers) {
                answerDAO.insertUserAnswer(new UserAnswer(user, question, interview, answer, currentDate));
            }
        }

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
