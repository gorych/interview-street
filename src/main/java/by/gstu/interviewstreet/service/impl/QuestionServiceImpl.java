package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.QuestionDAO;
import by.gstu.interviewstreet.dao.QuestionTypeDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionTypeDAO questionTypeDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Override
    @Transactional
    public Question get(int id) {
        return questionDAO.getById(id);
    }

    @Override
    @Transactional
    public QuestionType getType(int typeId) {
        return questionTypeDAO.getById(typeId);
    }

    @Override
    @Transactional
    public List<Question> getAllOrderByNumber(String hash) {
        return questionDAO.getAllOrderByNumber(hash);
    }

    @Override
    @Transactional
    public Question addDefaultQuestion(Interview interview, QuestionType questionType, int number) {
        Question question = new Question(interview, questionType, number, "Введите текст вопроса");
        List<Question> questions = questionDAO.getAllWhoseNumberMoreOrEquals(number);

        questionDAO.incrementNumbers(questions);
        questionDAO.saveOrUpdate(question);

        return question;
    }

    @Override
    @Transactional
    public void saveOrUpdate(Question question) {
        questionDAO.saveOrUpdate(question);
    }

    @Override
    @Transactional
    public void move(int questId, int number) {
        Question who = questionDAO.getById(questId);
        Question whom = questionDAO.getByNumber(number);

        int whoNumber = who.getNumber();
        int whomNumber = whom.getNumber();

        who.setNumber(whomNumber);
        whom.setNumber(whoNumber);

        questionDAO.saveOrUpdate(who);
        questionDAO.saveOrUpdate(whom);
    }

    @Override
    @Transactional
    public void remove(Question question) {
        List<Question> questions = questionDAO.getAllWhoseNumberMore(question.getNumber());

        questionDAO.decrementNumbers(questions);
        questionDAO.remove(question);
    }
}
