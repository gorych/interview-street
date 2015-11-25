package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IInterviewDAO;
import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
import by.gstu.interviewstreet.dao.IUserDAO;
import by.gstu.interviewstreet.dao.IUserInterviewDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.InterviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private IInterviewDAO interviewDAO;

    @Autowired
    IInterviewTypeDAO interviewTypeDAO;

    @Autowired
    private IUserInterviewDAO userInterviewDAO;

    @Override
    @Transactional
    public List<Interview> getAll() {
        return interviewDAO.getAllInterviews();
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
    public List<List<Form>> getAnswers(List<Form> questionForm) {
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
    public Interview get(int interviewId) {
        return interviewDAO.getInterviewById(interviewId);
    }

    @Override
    @Transactional
    public void insert(ExtendUserInterview userInterview) {
        Set<Post> posts = userInterview.getPosts();

        List<Integer> ids = new ArrayList<>();
        for (Post post : posts) {
            ids.add(post.getId());
        }

        List<User> users = userDAO.getUsersByPosts(ids);
        Interview interview = userInterview.getInterview();

        Calendar calender = Calendar.getInstance();
        java.util.Date utilDate = calender.getTime();
        java.sql.Date currentDate = new java.sql.Date(utilDate.getTime());

        int answerTypeId = interview.getType().getId();
        InterviewType type = interviewTypeDAO.getTypeById(answerTypeId);

        interview.setPlacementDate(currentDate);
        interview.setType(type);

        interviewDAO.insertInterview(interview, users);
    }

    @Override
    @Transactional
    public void remove(int[] ids) {
        interviewDAO.removeInterviews(ids);
    }

    @Override
    @Transactional
    public void hide(int id) {
        interviewDAO.hideInterview(id);
    }
}
