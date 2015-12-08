package by.gstu.interviewstreet.service.impl;

import by.gstu.interviewstreet.dao.IInterviewDAO;
import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
import by.gstu.interviewstreet.dao.IUserDAO;
import by.gstu.interviewstreet.dao.IUserInterviewDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.InterviewService;
import by.gstu.interviewstreet.web.param.RequestIdParam;
import by.gstu.interviewstreet.web.param.RequestParamException;
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
        return interviewDAO.getAll();
    }

    @Override
    @Transactional
    public List<Interview> getByType(int typeId) {
        if(typeId < 1 || typeId > 2){
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
        jsonObject.put("hide", interview.isHide() ? "lock" : "lock_open");
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
    public String getLightJSON(List<Interview> interviews) {

        List<Map<String, String>> jsonList = new ArrayList<>();

        for (Interview interview:interviews){
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
    public Interview insert(ExtendUserInterview userInterview) throws RequestParamException {
        Interview interview = userInterview.getInterview();

        Calendar calender = Calendar.getInstance();
        java.util.Date utilDate = calender.getTime();
        java.sql.Date currentDate = new java.sql.Date(utilDate.getTime());

        int interviewType = interview.getType().getId();
        InterviewType type = interviewTypeDAO.getById(interviewType);

        interview.setPlacementDate(currentDate);
        interview.setType(type);
        interview.setHide(true);
        interview.setHash(interview.hashCode());

        Set<Post> posts = userInterview.getPosts();
        if (interviewType == 2)/*so it is necessary*/ {
            interviewDAO.insert(interview);
            return interview;
        }
        List<Integer> ids = new ArrayList<>();
        for (Post post : posts) {
            ids.add(new RequestIdParam(post.getId()).intValue());
        }

        List<User> users = userDAO.getByPosts(ids);
        interviewDAO.insert(interview, users);

        return interview;
    }

    @Override
    @Transactional
    public void remove(List<Integer> ids) {
        interviewDAO.remove(ids);
    }

    @Override
    @Transactional
    public void hide(int id) {
        interviewDAO.hide(id);
    }
}
