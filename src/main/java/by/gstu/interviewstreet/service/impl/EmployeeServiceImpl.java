package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IEmployeeDAO;
import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    IEmployeeDAO employeeDAO;

    @Override
    @Transactional
    public List<Employee> getEmployeesBySubdivision(Object[] subdivisionIds) {
        return employeeDAO.getEmployeesBySubdivision(subdivisionIds);
    }

    @Override
    public String getJsonString(List<Employee> employees) {
        List<Map<String, String>> jsonList = new ArrayList<>();
        for (Employee employee : employees) {
            Map<String, String> jsonObject = new HashMap<>();
            jsonObject.put("post_id", employee.getPost().getId()+"");
            jsonObject.put("post_name", employee.getPost().getName());
            jsonList.add(jsonObject);
        }

        String jsonString = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(jsonList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
