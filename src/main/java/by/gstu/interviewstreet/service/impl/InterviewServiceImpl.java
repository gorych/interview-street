package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IEmployeeDAO;
import by.gstu.interviewstreet.dao.IInterviewDAO;
import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
import by.gstu.interviewstreet.dao.IUserInterviewDAO;
import by.gstu.interviewstreet.domain.Employee;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.UserInterview;
import by.gstu.interviewstreet.service.InterviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private IInterviewTypeDAO interviewTypeDAO;

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
    public List<Interview> getByType(int typeId) {
        if (typeId < 1 || typeId > 2) {
            return interviewDAO.getAll();
        }
        return interviewDAO.getByType(typeId);
    }

    @Override
    @Transactional
    public List<Form> getQuestions(int interviewId) {
        List<Form> forms = interviewDAO.getInterviewQuestions(interviewId);
        if (forms == null) {
            return new ArrayList<>();
        }
        return forms;
    }

    @Override
    @Transactional
    public List<Form> getQuestions(long hash) {
        List<Form> forms = interviewDAO.getInterviewQuestions(hash);
        if (forms == null) {
            return new ArrayList<>();
        }
        return forms;
    }

    @Override
    @Transactional
    public List<List<Form>> getAnswers(List<Form> questionForm) {
        return interviewDAO.getInterviewAnswers(questionForm);
    }

    @Override
    @Transactional
    public Map<String, Object> getValueMapForCard(int interviewId) {
        Interview interview = interviewDAO.getById(interviewId);
        List<UserInterview> userInterviews = userInterviewDAO.getById(interviewId);

        List<Integer> posts = new ArrayList<>();
        List<Integer> subIds = new ArrayList<>();
        for (UserInterview userInterview : userInterviews) {
            posts.add(userInterview.getUser().getEmployee().getPost().getId());
            subIds.add(userInterview.getUser().getEmployee().getSubdivision().getId());
        }

        List<Employee> employees = employeeDAO.getBySubdivisionIds(subIds);

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("activePosts", posts);
        valueMap.put("subs", subIds);
        valueMap.put("allPosts", employees);
        valueMap.put("interview", interview);

        return valueMap;
    }

    @Override
    public String getLightJSON(List<Interview> interviews) {
        List<Map<String, String>> jsonList = new ArrayList<>();

        for (Interview interview : interviews) {
            Map<String, String> jsonObject = new HashMap<>();
            jsonObject.put("id", interview.getId() + "");
            jsonObject.put("name", interview.getName());
            jsonList.add(jsonObject);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(jsonList);
        } catch (JsonProcessingException e) {
            return "";
        }
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
    public Interview save(Interview interview) {
        interviewDAO.save(interview);
        return interview;
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
