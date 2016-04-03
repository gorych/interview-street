package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Question;

import java.util.List;

public interface QuestionService {

    Question get(int id);

    Question add(Interview interview, int number);

}
