package by.gstu.interviewstreet.dao;


import by.gstu.interviewstreet.domain.Employee;

import java.util.Collection;
import java.util.List;

public interface EmployeeDAO {

    List<Employee> getBySubdivisionIds(Collection subdivisionIds);

}
