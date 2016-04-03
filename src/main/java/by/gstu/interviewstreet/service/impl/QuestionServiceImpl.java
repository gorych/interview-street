package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IAnswerDAO;
import by.gstu.interviewstreet.dao.IQuestionDAO;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.service.QuestionService;
import by.gstu.interviewstreet.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private IQuestionDAO questionDAO;

    @Autowired
    private IAnswerDAO answerDAO;

    @Override
    @Transactional
    public Question get(int id) {
        return questionDAO.getById(id);
    }

    @Override
    @Transactional
    public List<Question> getAllOrderByNumber(String hash) {
        return questionDAO.getAllOrderByNumber(hash);
    }

    @Override
    @Transactional
    public Question addQuestion(Interview interview, int number) {
        Question question = new Question(interview, number, "Введите текст вопроса");
        List<Question> questions = questionDAO.getAllWhoseNumberMoreOrEquals(number);

        questionDAO.incrementNumbers(questions);
        questionDAO.saveOrUpdate(question);

        return question;
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
    public Map<String, Object> getValueMapForQuestionForm(Question question, AnswerType answerType) {
        int answerCount = answerType.getAnswerCount();

        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < answerCount; i++) {
            Answer answer = new Answer(answerType, question, answerType.getDefaultValue());

            answerDAO.saveOrUpdate(answer);
            answers.add(answer);
        }

        String[] keys = new String[]{"answerType", "question", "answers"};
        Object[] objects = new Object[]{answerType, question, answers};

        return ServiceUtils.buildValueMap(keys, objects);
    }

    //TODO delete duplicate code

    @Override
    @Transactional
    public Map<String, Object> getValueMapForDuplicateQuestionForm(Question question, Question duplicate, AnswerType answerType) {

        List<Answer> answers = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            Answer duplicateAnswer = new Answer(answer.getType(), duplicate, answer.getText());

            answerDAO.saveOrUpdate(duplicateAnswer);
            answers.add(answer);
        }

        String[] keys = new String[]{"answerType", "question", "answers"};
        Object[] objects = new Object[]{answerType, duplicate, answers};

        return ServiceUtils.buildValueMap(keys, objects);
    }

    @Override
    @Transactional
    public void remove(Question question) {
        List<Question> questions = questionDAO.getAllWhoseNumberMore(question.getNumber());

        questionDAO.decrementNumbers(questions);
        questionDAO.remove(question);
    }
}
