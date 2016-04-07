package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.AnswerDAO;
import by.gstu.interviewstreet.dao.AnswerTypeDAO;
import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.domain.QuestionType;
import by.gstu.interviewstreet.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    private static final String TEXT_ANSWER_NAME = "text";

    @Autowired
    private AnswerDAO answerDAO;

    @Autowired
    private AnswerTypeDAO answerTypeDAO;

    @Override
    @Transactional
    public Answer get(int id) {
        return answerDAO.getById(id);
    }

    @Override
    @Transactional
    public List<Answer> get(List<Integer> ids) {
        return answerDAO.getByIds(ids);
    }

    @Override
    @Transactional
    public List<Answer> addDefaultAnswers(Question question) {
        QuestionType questionType = question.getType();
        AnswerType answerType = questionType.getAnswerType();

        int answerCount = questionType.getAnswerCount();
        String defaultValue = answerType.getDefaultValue();

        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < answerCount; i++) {
            Answer answer = new Answer(answerType, question, defaultValue);

            answerDAO.saveOrUpdate(answer);
            answers.add(answer);
        }

        return answers;
    }

    @Override
    @Transactional
    public List<Answer> duplicateAnswers(Question question, Question duplicate) {
        List<Answer> answers = new ArrayList<>();

        for (Answer answer : question.getAnswers()) {
            Answer duplicateAnswer = new Answer(answer.getType(), duplicate, answer.getText());

            answerDAO.saveOrUpdate(duplicateAnswer);
            answers.add(duplicateAnswer);
        }

        return answers;
    }

    @Override
    @Transactional
    public Answer addDefaultAnswer(AnswerType type, Question question) {
        Answer answer = new Answer(type, question, type.getDefaultValue());
        answerDAO.saveOrUpdate(answer);

        return answer;
    }

    @Override
    @Transactional
    public Answer addDefaultTextAnswer(Question question) {
        AnswerType answerType = answerTypeDAO.getByName(TEXT_ANSWER_NAME);
        Answer answer = new Answer(answerType, question, answerType.getDefaultValue());
        answerDAO.saveOrUpdate(answer);

        return answer;
    }

    @Override
    @Transactional
    public void remove(Answer answer) {
        answerDAO.remove(answer);
    }
}
