package by.gstu.interviewstreet.dao;


import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.domain.Post;
import by.gstu.interviewstreet.domain.Subdivision;

import java.util.Collection;
import java.util.List;

public interface EmployeeDAO extends GenericDAO<Employee, Integer> {

    List<Employee> getBySubdivisionIds(Collection subdivisionIds);

    Employee findByAll(String firstName, String secondName, String lastName, Subdivision sub, Post post);

}
