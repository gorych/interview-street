package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.bean.StatisticData;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.PublishedInterview;
import by.gstu.interviewstreet.domain.Subdivision;

import java.util.Date;
import java.util.List;

public interface StatisticsService {

    List<StatisticData> getInterviewStatistics(Interview interview, Subdivision sub, PublishedInterview publish);

}
