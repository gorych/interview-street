package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;

import java.util.List;

public interface UserAnswerService {

    void save(User user, Interview interview, List<Answer> answers);

}
