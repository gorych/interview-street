package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.Question;

public interface QuestionService {

    long insert();

    Question get(int id);
}
