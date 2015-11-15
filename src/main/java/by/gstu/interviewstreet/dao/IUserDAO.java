package by.gstu.interviewstreet.dao;


import by.gstu.interviewstreet.domain.User;

public interface IUserDAO {

    User getUserByPassportData(String passportData);

}
