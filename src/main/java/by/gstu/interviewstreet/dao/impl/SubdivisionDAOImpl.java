package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.SubdivisionDAO;
import by.gstu.interviewstreet.domain.Subdivision;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubdivisionDAOImpl extends AbstractDbDAO implements SubdivisionDAO {

    @Override
    public Subdivision getById(Integer id) {
        return (Subdivision) getSession()
                .createQuery("FROM Subdivision WHERE id=:id")
                .setInteger("id", id).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Subdivision> getAll() {
        return getSession().createQuery("FROM Subdivision ORDER BY name ASC").list();
    }
}
