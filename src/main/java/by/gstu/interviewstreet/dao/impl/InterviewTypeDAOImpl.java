package by.gstu.interviewstreet.dao.impl;

        import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
        import by.gstu.interviewstreet.domain.InterviewType;
        import org.springframework.stereotype.Repository;
        import org.springframework.transaction.annotation.Transactional;

@Repository
public class InterviewTypeDAOImpl extends AbstractDbDAO implements IInterviewTypeDAO {

    @Override
    @Transactional
    public InterviewType getById(int id) {
        return (InterviewType) getSession()
                .createQuery("FROM InterviewType WHERE id = :id")
                .setInteger("id", id)
                .uniqueResult();
    }
}
