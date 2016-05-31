package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.InterviewType;

public interface InterviewTypeDAO extends GenericDAO<InterviewType, Integer> {

    InterviewType getByName(String name);

}
