package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.ISubdivisionDAO;
import by.gstu.interviewstreet.domain.Subdivision;
import by.gstu.interviewstreet.service.SubdivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubdivisionServiceImpl implements SubdivisionService{

    @Autowired
    ISubdivisionDAO subdivisionDAO;

    @Override
    @Transactional
    public List<Subdivision> getAllSubdivisions() {
        return subdivisionDAO.getAllSubdivisions();
    }
}
