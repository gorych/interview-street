package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.impl.InterviewDAOImpl;
import by.gstu.interviewstreet.dao.impl.UserInterviewDAOImpl;
import by.gstu.interviewstreet.domain.Form;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.domain.User;
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
    private InterviewDAOImpl interviewDAO;

    @Autowired
    private UserInterviewDAOImpl userInterviewDAO;

    @Override
    @Transactional
    public List<Interview> getAllInterviews() {
        return interviewDAO.getAllInterviews();
    }

    @Override
    @Transactional
    public List<Form> getInterviewQuestions(int interviewId) {
        return interviewDAO.getInterviewQuestions(interviewId);
    }

    @Override
    @Transactional
    public List<List<Form>> getInterviewAnswers(List<Form> questionForm) {
        return interviewDAO.getInterviewAnswers(questionForm);
    }

    @Override
    @Transactional
    public String getJsonString(int interviewId) {
        Interview interview = interviewDAO.getInterviewById(interviewId);
        List<UserInterview> userInterviews = userInterviewDAO.getUserInterviewsById(interviewId);

        StringBuilder posts = new StringBuilder();
        StringBuilder subdivisions = new StringBuilder();
        for (UserInterview ui : userInterviews) {
            int postName = ui.getUser().getEmployee().getPost().getId();
            int subdivisionName = ui.getUser().getEmployee().getSubdivision().getId();

            posts.append(postName).append(",");
            subdivisions.append(subdivisionName).append(",");
        }
        posts.deleteCharAt(posts.length() - 1);
        subdivisions.deleteCharAt(subdivisions.length() - 1);

        List<Map<String, String>> jsonList = new ArrayList<>();
        Map<String, String> jsonObject = new HashMap<>();

        jsonObject.put("type", interview.getType().getId() + "");
        jsonObject.put("name", interview.getName());
        jsonObject.put("description", interview.getDescription());
        jsonObject.put("subdivisions", subdivisions.toString());
        jsonObject.put("posts", posts.toString());

        jsonList.add(jsonObject);

        String jsonString = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(jsonList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    @Transactional
    public Interview getInterviewById(int interviewId) {
        return interviewDAO.getInterviewById(interviewId);
    }

    @Override
    @Transactional
    public void insertInterview(Interview interview) {
        interviewDAO.insertInterview(interview);
    }

    @Override
    @Transactional
    public void insertInterview(Interview interview, List<User> users) {
        interviewDAO.insertInterview(interview, users);
    }

    @Override
    @Transactional
    public void removeInterviews(int[] ids) {
        interviewDAO.removeInterviews(ids);
    }

    @Override
    @Transactional
    public void hideInterview(int id) {
        interviewDAO.hideInterview(id);
    }
}
