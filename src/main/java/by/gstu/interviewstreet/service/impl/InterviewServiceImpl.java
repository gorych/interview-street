package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.EmployeeDAO;
import by.gstu.interviewstreet.dao.InterviewDAO;
import by.gstu.interviewstreet.dao.SubdivisionDAO;
import by.gstu.interviewstreet.dao.UserInterviewDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.web.AttrConstants;
import by.gstu.interviewstreet.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private InterviewDAO interviewDAO;

    @Autowired
    private SubdivisionDAO subdivisionDAO;

    @Autowired
    private UserInterviewDAO userInterviewDAO;

    @Override
    @Transactional
    public List<Interview> getAll() {
        return interviewDAO.getAll();
    }

    @Override
    @Transactional
    public List<Interview> getAllInRangeByUser(int from, int howMany, String userCredential) {
        return interviewDAO.getAllInRange(from, howMany, userCredential);
    }

    @Override
    @Transactional
    public Map<String, Object> getModelMapForEditForm(int interviewId) {
        Interview interview = interviewDAO.getById(interviewId);
        List<UserInterview> userInterviews = userInterviewDAO.getByInterviewAndGroupByPost(interviewId);

        List<Post> activePosts = new ArrayList<>();
        List<Integer> activeSubIds = new ArrayList<>();

        for (UserInterview userInterview : userInterviews) {
            activePosts.add(userInterview.getUser().getEmployee().getPost());
            activeSubIds.add(userInterview.getUser().getEmployee().getSubdivision().getId());
        }

        Map<Object, String> posts = new HashMap<>();
        Map<Object, String> subdivisions = new TreeMap<>();
        if (activeSubIds.size() > 0) {

            /*List of employees which have such subdivisions*/
            List<Employee> employees = employeeDAO.getBySubdivisionIds(activeSubIds);

            for (Employee employee : employees) {
                Post post = employee.getPost();

                /*Mark post if it was selected. Used on form.jsp*/
                posts.put(post, activePosts.contains(post) ? "selected" : "not_selected");
            }

            /*List of subs for select, which used on form.jsp*/
            List<Subdivision> subs = subdivisionDAO.getAll();

            for (Subdivision sub : subs) {
                subdivisions.put(sub, activeSubIds.contains(sub.getId()) ? "selected" : "not_selected");
            }

        }

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put(AttrConstants.POSTS, posts);
        valueMap.put(AttrConstants.SUBDIVISIONS, subdivisions);
        valueMap.put(AttrConstants.INTERVIEW, interview);

        return valueMap;
    }

    @Override
    @Transactional
    public Interview get(int interviewId) {
        return interviewDAO.getById(interviewId);
    }

    @Override
    @Transactional
    public Interview get(String hash) {
        return interviewDAO.getByHash(hash);
    }

    @Override
    @Transactional
    public Interview saveOrUpdate(Interview interview) {
        Interview existed = interviewDAO.getById(interview.getId());

        /*create new interview*/
        if (existed == null) {
            byte[] bytes = (interview.getName()+ System.currentTimeMillis()).getBytes();
            interview.setHash(DigestUtils.md5DigestAsHex(bytes));
            interviewDAO.saveOrUpdate(interview);

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
        existed.setSecondPassage(interview.isSecondPassage());

        existed.setHide(true);

        interviewDAO.saveOrUpdate(existed);
        return existed;
    }

    @Override
    @Transactional
    public void update(Interview interview) {
        interviewDAO.saveOrUpdate(interview);
    }

    private void removeAllUserInterviews(Interview interview) {
        List<UserInterview> userInterviews = userInterviewDAO.getByInterviewAndGroupByPost(interview.getId());
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
    public void hideExpiredInterviews() {
        List<Interview> interviews = interviewDAO.getAll();
        interviews.stream().filter(Interview::getIsDeadline).forEach(interview -> {
            interview.setHide(true);
            interviewDAO.saveOrUpdate(interview);
        });
    }

    @Override
    @Transactional
    public void lockOrUnlock(int id) {
        interviewDAO.lockOrUnlock(id);
    }
}
