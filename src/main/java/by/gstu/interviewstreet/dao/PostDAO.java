package by.gstu.interviewstreet.dao;


import by.gstu.interviewstreet.domain.Post;
import by.gstu.interviewstreet.domain.Subdivision;

public interface PostDAO extends GenericDAO<Post, Integer> {

    Post findByName(String name);
}
