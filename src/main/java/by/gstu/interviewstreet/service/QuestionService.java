package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.AnswerType;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.domain.QuestionType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface QuestionService {

    Question get(int id);

    Question get(String hash, int questId);

    QuestionType getType(int typeId);

    List<Question> getAllOrderByNumber(String hash);

    Question addDefaultQuestion(Interview interview, QuestionType questionType, int number);

    void saveOrUpdate(Question question);

    void move(int questId, int number);

    void remove(Question question);

}
