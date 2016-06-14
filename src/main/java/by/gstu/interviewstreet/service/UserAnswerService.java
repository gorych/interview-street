package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
import by.gstu.interviewstreet.domain.UserAnswer;

import java.util.List;

public interface UserAnswerService {

    void save(User user, Interview interview, List<Answer> answers);

    List<String> getRespondentList(String hash, String text);

}
