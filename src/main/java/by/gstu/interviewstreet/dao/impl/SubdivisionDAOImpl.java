package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.ISubdivisionDAO;
import by.gstu.interviewstreet.domain.Subdivision;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubdivisionDAOImpl extends AbstractDbDAO implements ISubdivisionDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Subdivision> getAll() {
        return getSession().createQuery("from Subdivision ").list();
    }
}
