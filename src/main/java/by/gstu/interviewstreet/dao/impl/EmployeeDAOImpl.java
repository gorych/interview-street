package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IEmployeeDAO;
import by.gstu.interviewstreet.domain.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public class EmployeeDAOImpl extends AbstractDbDAO implements IEmployeeDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> getBySubdivisionIds(Collection subdivisionIds) {
        return getSession()
                .createQuery("FROM Employee WHERE subdivision.id IN (:subdivisionIds) GROUP BY post.id")
                .setParameterList("subdivisionIds", subdivisionIds)
                .list();
    }
}
