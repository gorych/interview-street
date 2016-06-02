package by.gstu.interviewstreet.service;

import by.gstu.interviewstreet.domain.User;

import javax.naming.directory.Attributes;

public interface LdapService {

    User buildUser(Attributes attributes, String username);

}
