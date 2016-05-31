package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.AnswerType;

public interface AnswerTypeDAO extends GenericDAO<AnswerType, Integer> {

    AnswerType getByName(String name);

}
