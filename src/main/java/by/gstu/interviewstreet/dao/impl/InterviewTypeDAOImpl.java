package by.gstu.interviewstreet.dao.impl;

        import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
        import by.gstu.interviewstreet.domain.InterviewType;
        import org.springframework.stereotype.Repository;

@Repository
public class InterviewTypeDAOImpl extends AbstractDbDAO implements IInterviewTypeDAO {

    @Override
    public InterviewType getById(int id) {
        return (InterviewType) getSession()
                .createQuery("FROM InterviewType WHERE id = :id")
                .setInteger("id", id)
                .uniqueResult();
    }
}
