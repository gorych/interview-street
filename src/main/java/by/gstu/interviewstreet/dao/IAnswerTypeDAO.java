package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.AnswerType;

import java.util.List;

public interface IAnswerTypeDAO {

    AnswerType getDefaultAnswerType();

    AnswerType getAnswerTypeById(int id);

    List<AnswerType> getAnswerTypesByIds(Integer[] ids);

}
