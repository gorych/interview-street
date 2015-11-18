package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IEmployeeDAO;
import by.gstu.interviewstreet.domain.Employee;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements IEmployeeDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> getEmployeesBySubdivision(int subdivisionId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Employee WHERE subdivision.id=:subdivision");
        query.setInteger("subdivision", subdivisionId);
        return query.list();
    }
}
