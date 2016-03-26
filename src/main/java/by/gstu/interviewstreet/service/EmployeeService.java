package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.Employee;

import java.util.Collection;
import java.util.List;

public interface EmployeeService {

    List<Employee> getBySubdivisions(Collection subdivisionIds);

}
