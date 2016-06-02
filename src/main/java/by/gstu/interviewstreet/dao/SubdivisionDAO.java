package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.Subdivision;

public interface SubdivisionDAO extends GenericDAO<Subdivision, Integer> {

    Subdivision findByName(String name);

}
