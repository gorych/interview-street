package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.InterviewType;

public interface InterviewTypeDAO {

    InterviewType getById(int id);

    InterviewType getByName(String name);

}
