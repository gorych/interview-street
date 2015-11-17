package by.gstu.interviewstreet.service;


import by.gstu.interviewstreet.domain.User;

public interface UserService {

    User getUserByPassportData(String passportData);

}
