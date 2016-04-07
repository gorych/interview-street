package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.PostDAO;
import by.gstu.interviewstreet.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDAOImpl extends AbstractDbDAO implements PostDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Post> getAll() {
        return getSession()
                .createQuery("from Post")
                .list();
    }
}
