package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IInterviewDAO;
import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
import by.gstu.interviewstreet.dao.IUserDAO;
import by.gstu.interviewstreet.dao.IUserInterviewDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.InterviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private IInterviewDAO interviewDAO;

    @Autowired
    IInterviewTypeDAO interviewTypeDAO;

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
    public String getJSON(Interview interview) {
        final int OPEN_INTERVIEW = 1;

        StringBuilder posts = new StringBuilder();
        StringBuilder subdivisions = new StringBuilder();
        StringBuilder subdivisionNames = new StringBuilder();

        List<UserInterview> userInterviews = userInterviewDAO.getById(interview.getId());
        if (userInterviews != null && userInterviews.size() != 0) {
            for (UserInterview ui : userInterviews) {
                int postId = ui.getUser().getEmployee().getPost().getId();
                int subdivisionId = ui.getUser().getEmployee().getSubdivision().getId();
                String subdivisionName = ui.getUser().getEmployee().getSubdivision().getName();

                posts.append(postId).append(",");
                subdivisions.append(subdivisionId).append(",");
                subdivisionNames.append(subdivisionName).append(",");
            }
            posts.deleteCharAt(posts.length() - 1);
            subdivisions.deleteCharAt(subdivisions.length() - 1);
            subdivisionNames.deleteCharAt(subdivisionNames.length() - 1);
        }

        List<Map<String, String>> jsonList = new ArrayList<>();
        Map<String, String> jsonObject = new HashMap<>();

        jsonObject.put("id", interview.getId() + "");
        jsonObject.put("name", interview.getName());
        jsonObject.put("date", interview.getPlacementDate() + "");
        jsonObject.put("type_id", interview.getType().getId() + "");
        jsonObject.put("type", interview.getType().getId() <= OPEN_INTERVIEW ? "visibility" : "visibility_off");
        jsonObject.put("lock", interview.getHide() ? "lock" : "lock_open");
        jsonObject.put("description", interview.getDescription());
        jsonObject.put("subdivisions", subdivisions.toString());
        jsonObject.put("subdvsn_names", subdivisionNames.toString());
        jsonObject.put("posts", posts.toString());
        jsonObject.put("interview_id", interview.getId() + "");

        jsonList.add(jsonObject);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(jsonList);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Override
    @Transactional
    public String getJson(int interviewId) {
        Interview interview = interviewDAO.getById(interviewId);
        List<UserInterview> userInterviews = userInterviewDAO.getById(interviewId);

        JSONArray jsonPosts = new JSONArray();
        JSONArray jsonSubdivisions = new JSONArray();
        for (int i = 0; i < userInterviews.size(); i++) {
            UserInterview userInterview = userInterviews.get(i);

            int postId = userInterview.getUser().getEmployee().getPost().getId();
            int subdivisionId = userInterview.getUser().getEmployee().getSubdivision().getId();

            jsonPosts.put(i, postId);
            jsonSubdivisions.put(i, subdivisionId);
        }

        JSONObject json = new JSONObject();
        json.put("posts", jsonPosts);
        json.put("subdivisions", jsonSubdivisions);
        json.put("interview", new JSONObject(interview));

        return json.toString();
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
    public void lock(int id) {
        interviewDAO.lock(id);
    }
}
