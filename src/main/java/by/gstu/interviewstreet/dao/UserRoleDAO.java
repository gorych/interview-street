package by.gstu.interviewstreet.dao;

import by.gstu.interviewstreet.domain.UserRole;

public interface UserRoleDAO extends GenericDAO<UserRole, Integer> {

    UserRole getByName(String name);

}
