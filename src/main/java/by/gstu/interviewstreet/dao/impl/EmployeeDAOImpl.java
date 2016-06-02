package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.EmployeeDAO;
import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.domain.Post;
import by.gstu.interviewstreet.domain.Subdivision;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class EmployeeDAOImpl extends GenericDAOImpl<Employee, Integer> implements EmployeeDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> getBySubdivisionIds(Collection subdivisionIds) {
        return currentSession()
                .createQuery("FROM Employee WHERE subdivision.id IN (:subdivisionIds) GROUP BY post.id ORDER BY post.name ASC")
                .setParameterList("subdivisionIds", subdivisionIds)
                .list();
    }

    @Override
    public Employee findByAll(String firstName, String secondName, String lastName, Subdivision sub, Post post) {
        return (Employee) currentSession()
                .createQuery("FROM Employee WHERE firstname LIKE :firstname AND secondname LIKE :secondname " +
                        "AND lastname LIKE :lastname AND subdivision.id = :subId AND post.id = :postId")
                .setString("firstname", firstName)
                .setString("secondname", secondName)
                .setString("lastname", lastName)
                .setInteger("subId", sub.getId())
                .setInteger("postId", post.getId())
                .uniqueResult();
    }
}
