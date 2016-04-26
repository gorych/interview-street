package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.bean.StatisticData;
import by.gstu.interviewstreet.domain.Interview;

import java.util.List;

public interface StatisticsService {

    List<StatisticData> getInterviewStatistics(Interview interview);

}
