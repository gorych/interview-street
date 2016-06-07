package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.UserRoleDAO;
import by.gstu.interviewstreet.domain.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleDAOImpl extends GenericDAOImpl<UserRole, Integer> implements UserRoleDAO {

    @Override
    public UserRole getByName(String name) {
        return (UserRole) currentSession().createQuery("FROM UserRole WHERE name LIKE :name")
                .setString("name", name)
                .uniqueResult();
    }

}
