package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.*;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private IAnswerDAO answerDAO;

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
    public Map<String, Object> getValueMapForQuestionForm(Question question, AnswerType answerType) {
        int answerCount = answerType.getAnswerCount();

        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < answerCount; i++) {
            Answer answer = new Answer(answerType, question, answerType.getDefaultValue());

            answerDAO.insert(answer);
            answers.add(answer);
        }

        Map<String, Object> valueMap = new HashMap<>();

        valueMap.put("answerType", answerType);
        valueMap.put("question", question);
        valueMap.put("answers", answers);

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
            byte[] bytes = interview.getName().getBytes();
            interview.setHash(DigestUtils.md5DigestAsHex(bytes) + System.currentTimeMillis());
            interviewDAO.save(interview);

            return interview;
        }
        removeAllUserInterviews(existed);

        existed.setName(interview.getName());
        existed.setType(interview.getType());
        existed.setGoal(interview.getGoal());
        existed.setEndDate(interview.getEndDate());
        existed.setAudience(interview.getAudience());
        existed.setPlacementDate(DateUtils.getNow());
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
