package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.EmployeeDAO;
import by.gstu.interviewstreet.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class EmployeeDAOImpl extends AbstractDbDAO implements EmployeeDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> getBySubdivisionIds(Collection subdivisionIds) {
        return getSession()
                .createQuery("FROM Employee WHERE subdivision.id IN (:subdivisionIds) GROUP BY post.id ORDER BY post.name ASC")
                .setParameterList("subdivisionIds", subdivisionIds)
                .list();
    }
}
