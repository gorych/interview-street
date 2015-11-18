package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
import by.gstu.interviewstreet.domain.InterviewType;
import by.gstu.interviewstreet.service.InterviewTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterviewTypeServiceImpl implements InterviewTypeService {

    @Autowired
    IInterviewTypeDAO interviewTypeDAO;

    @Override
    @Transactional
    public InterviewType getInterviewTypeById(int id) {
        return interviewTypeDAO.getTypeById(id);
    }
}
