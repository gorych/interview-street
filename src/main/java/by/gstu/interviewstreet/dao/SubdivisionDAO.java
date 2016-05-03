package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.Subdivision;

import java.util.List;

public interface SubdivisionDAO {

    Subdivision getById(Integer id);

    List<Subdivision> getAll();

}
