package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.*;

import java.util.List;

public interface UserAnswerService {

    void save(ExpertInterview expert, User user, Interview interview, List<Answer> answers);

    List<String> getRespondentList(Interview interview, String text);

    String getRespondentListHowLine(Interview interview, String text);

}
