package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.SubdivisionDAO;
import by.gstu.interviewstreet.domain.Subdivision;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubdivisionDAOImpl extends GenericDAOImpl<Subdivision, Integer> implements SubdivisionDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Subdivision> getAll() {
        return currentSession().createQuery("FROM Subdivision ORDER BY name ASC").list();
    }

    @Override
    public Subdivision findByName(String name) {
        return (Subdivision) currentSession().createQuery("FROM Subdivision WHERE name LIKE :name").
                setString("name", name)
                .uniqueResult();
    }
}
