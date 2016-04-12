package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.EmployeeDAO;
import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDAO employeeDAO;

    @Override
    @Transactional
    public List<Employee> getBySubdivisions(Collection subdivisionIds) {
        return employeeDAO.getBySubdivisionIds(subdivisionIds);
    }

}
