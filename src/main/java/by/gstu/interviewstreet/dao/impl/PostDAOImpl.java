package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.IPostDAO;
import by.gstu.interviewstreet.domain.Post;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDAOImpl implements IPostDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Post> getAllPosts() {
        return sessionFactory.getCurrentSession().createQuery("from Post")
                .list();
    }
}
