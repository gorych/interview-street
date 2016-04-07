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

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

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
    public AnswerType getAnswerType(int id) {
        return answerTypeDAO.getById(id);
    }

    //TODO duplicate code

    @Override
    @Transactional
    public void addDefaultAnswers(AnswerType type, Question question) {
        QuestionType questionType = question.getType();
        int answerCount = questionType.getAnswerCount();

        for (int i = 0; i < answerCount; i++) {
            answerDAO.saveOrUpdate(new Answer(type, question, type.getDefaultValue()));
        }
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
        //TODO getByName
        AnswerType answerType = answerTypeDAO.getById(1);
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
