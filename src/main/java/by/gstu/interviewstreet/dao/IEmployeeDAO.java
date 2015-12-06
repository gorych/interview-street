package by.gstu.interviewstreet.dao;


import by.gstu.interviewstreet.domain.Employee;

import java.util.List;

public interface IEmployeeDAO {

    List<Employee> getBySubdivision(Object[] subdivisionIds);

}
