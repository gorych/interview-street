package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.ISubdivisionDAO;
import by.gstu.interviewstreet.domain.Subdivision;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubdivisionDAOImpl implements ISubdivisionDAO{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Subdivision> getAllSubdivisions() {
        return sessionFactory.getCurrentSession().createQuery("from Subdivision ").list();
    }
}
