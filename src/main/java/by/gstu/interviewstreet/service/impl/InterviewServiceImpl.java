package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IEmployeeDAO;
import by.gstu.interviewstreet.dao.IInterviewDAO;
import by.gstu.interviewstreet.dao.IUserInterviewDAO;
import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private IEmployeeDAO employeeDAO;

    @Autowired
    private IInterviewDAO interviewDAO;

    @Autowired
    private IUserInterviewDAO userInterviewDAO;

    @Override
    @Transactional
    public List<Interview> getAll() {
        return interviewDAO.getAll();
    }

    @Override
    @Transactional
    public Map<String, Object> getValueMapForCard(int interviewId) {
        Interview interview = interviewDAO.getById(interviewId);
        List<UserInterview> userInterviews = userInterviewDAO.getByInterviewId(interviewId);

        List<Integer> posts = new ArrayList<>();
        List<Integer> subIds = new ArrayList<>();
        for (UserInterview userInterview : userInterviews) {
            posts.add(userInterview.getUser().getEmployee().getPost().getId());
            subIds.add(userInterview.getUser().getEmployee().getSubdivision().getId());
        }

        Map<String, Object> valueMap = new HashMap<>();

        if (subIds.size() > 0) {
            List<Employee> employees = employeeDAO.getBySubdivisionIds(subIds);
            valueMap.put("allPosts", employees);
        }

        valueMap.put("subs", subIds);
        valueMap.put("activePosts", posts);
        valueMap.put("interview", interview);

        return valueMap;
    }

    @Override
    @Transactional
    public Interview get(int interviewId) {
        return interviewDAO.getById(interviewId);
    }

    @Override
    @Transactional
    public Interview get(long hash) {
        return interviewDAO.getByHash(hash);
    }

    @Override
    @Transactional
    public Interview saveOrUpdate(Interview interview) {
        Interview existed = interviewDAO.getById(interview.getId());

        /*create new interview*/
        if (existed == null) {
            interviewDAO.save(interview);
            return interview;
        }

        removeAllUserInterviews(existed);

        existed.setName(interview.getName());
        existed.setType(interview.getType());
        existed.setGoal(interview.getGoal());
        existed.setEndDate(interview.getEndDate());
        existed.setAudience(interview.getAudience());
        existed.setPlacementDate(DateUtils.getToday());
        existed.setDescription(interview.getDescription());
        existed.setHide(true);

        interviewDAO.save(existed);
        return existed;
    }

    private void removeAllUserInterviews(Interview interview) {
        List<UserInterview> userInterviews = userInterviewDAO.getByInterviewId(interview.getId());
        for (UserInterview ui : userInterviews) {
            userInterviewDAO.remove(ui);
        }
    }

    @Override
    @Transactional
    public void remove(Interview interview) {
        interviewDAO.remove(interview);
    }

    @Override
    @Transactional
    public void lockOrUnlock(int id) {
        interviewDAO.lockOrUnlock(id);
    }
}
