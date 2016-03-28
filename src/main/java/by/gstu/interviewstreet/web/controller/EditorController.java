package by.gstu.interviewstreet.web.controller;


import by.gstu.interviewstreet.dao.IInterviewTypeDAO;
import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttributeConstants;
import by.gstu.interviewstreet.web.ParameterConstants;
import by.gstu.interviewstreet.web.param.ReqParam;
import by.gstu.interviewstreet.web.param.RequestIdParam;
import by.gstu.interviewstreet.web.param.RequestParamException;
import by.gstu.interviewstreet.web.param.RequestTextParam;
import by.gstu.interviewstreet.web.util.JSONParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
//@Secured(UserRoleConstants.EDITOR)
public class EditorController {

    @Autowired
    FormService formService;

    @Autowired
    AnswerService answerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    QuestionService questionService;

    @Autowired
    InterviewService interviewService;

    @Autowired
    IInterviewTypeDAO interviewTypeDAO;

    @Autowired
    SubdivisionService subdivisionService;

    @Autowired
    UserInterviewService userInterviewService;

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String showInterviewList(Model model) {
        model.addAttribute(AttributeConstants.INTERVIEWS, interviewService.getAll());
        model.addAttribute(AttributeConstants.SUBDIVISIONS, subdivisionService.getAll());

        return "interview-list";
    }

    @ResponseBody
    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public String loadPosts(@RequestBody String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        Type type = new TypeToken<List<Integer>>() { }.getType();
        List<Integer> subdivisionIds = JSONParser.convertJsonElementToObject(jsonArray, type);
        List<Employee> employees = employeeService.getBySubdivisions(subdivisionIds);

        return JSONParser.convertObjectToJsonString(employees);
    }

    @ResponseBody
    @RequestMapping(value = {"/save-interview"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public String processAddInterviewForm(@RequestBody String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        JsonElement interviewElement = jsonArray.get(0);
        JsonArray idsArray = jsonArray.get(1).getAsJsonArray();

        Interview interview = JSONParser.convertJsonElementToObject(interviewElement, Interview.class);
        interview = interviewService.saveOrUpdate(interview);

        if (interview.getType().isOpen()) {
            Integer[] postIds = JSONParser.convertJsonElementToObject(idsArray, Integer[].class);
            userInterviewService.addInterviewToUserByPost(interview, postIds);
        }

        return JSONParser.convertObjectToJsonString(interview.getId());
    }

    @ResponseBody
    @RequestMapping(value = {"/delete-interview"}, method = RequestMethod.POST)
    public String deleteInterview(@RequestParam String data) {
        Interview interview = JSONParser.convertJsonStringToObject(data, Interview.class);
        interviewService.remove(interview);

        return AttributeConstants.SUCCESS_RESPONSE_BODY;
    }

    @ResponseBody
    @RequestMapping(value = {"/lock-interview/{interviewId}"}, method = RequestMethod.GET)
    public String lockOrUnlockInterview(@PathVariable int interviewId) {
        interviewService.lockOrUnlock(interviewId);

        return AttributeConstants.SUCCESS_RESPONSE_BODY;
    }

    @ResponseBody
    @RequestMapping(value = {"/load-card-values"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    public String loadValuesForCard(@RequestParam int interviewId) {
        Map<String, Object> valueMap = interviewService.getValueMapForCard(interviewId);

        return JSONParser.convertObjectToJsonString(valueMap);
    }

    @ResponseBody
    @RequestMapping(value = {"/build-form"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public String buildQuestionForm(int hash, int answerTypeId) {
        AnswerType answerType = null;
        Interview interview = null;
        try {
            answerType = answerService.getAnswerType(answerTypeId);
            interview = interviewService.get(hash);
        } catch (RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }

        if (interview == null || answerType == null) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }

        List<Form> forms = formService.buildQuestionForm(interview, answerType);

        return JSONParser.convertObjectToJsonString(forms);

    }

    //region Form building

    @RequestMapping(value = {"/designer/{interviewId}"}, method = RequestMethod.GET)
    public String showQuestionsEditor(@PathVariable int interviewId, Model model) {
        List<Form> questionForms = interviewService.getQuestions(interviewId);
        List<List<Form>> answerForms = interviewService.getAnswers(questionForms);

        model.addAttribute(AttributeConstants.QUESTION_FORMS, questionForms);
        model.addAttribute(AttributeConstants.ANSWER_FORMS, answerForms);

        Interview interview = interviewService.get(interviewId);
        model.addAttribute(AttributeConstants.INTERVIEW, interview);

        return "test";
    }


    @ResponseBody
    @RequestMapping(value = {"/create-answer/{interviewId}/{questionId}"}, method = RequestMethod.GET)
    public long createAnswer(@PathVariable int interviewId, @PathVariable int questionId) {
        return -1;
    }

    @ResponseBody
    @RequestMapping(value = {"/load-question/{questionId}"}, method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    public String loadQuestion(@PathVariable Integer questionId) {
        try {
            return formService.getJSON(questionId);
        } catch (RuntimeException e) {
            return AttributeConstants.EMPTY_BODY;
        }
    }

    @RequestMapping(value = {"/send-form"}, method = RequestMethod.POST)
    @ResponseBody
    public String sendForm(HttpServletRequest req) {
        try {
            ReqParam questionTextParam = new RequestTextParam(req.getParameter(ParameterConstants.QUESTION_TEXT));
            ReqParam questionIdParam = new RequestIdParam(req.getParameter(ParameterConstants.QUESTION_ID));
            ReqParam answerTypeParam = new RequestIdParam(req.getParameter(ParameterConstants.ANSWER_TYPE_ID));

            String[] answerIdValues = req.getParameterValues(ParameterConstants.ANSWER_ID);
            String[] answerTextValues = req.getParameterValues(ParameterConstants.ANSWER_TEXT);
            if (answerIdValues.length != answerTextValues.length) {
                return AttributeConstants.ERROR_RESPONSE_BODY;
            }

            List<Integer> answerIds = new ArrayList<>();
            List<ReqParam> answerTexts = new ArrayList<>();

            for (int i = 0; i < answerIdValues.length; i++) {
                answerIds.add(new RequestIdParam(answerIdValues[i]).intValue());
                answerTexts.add(new RequestTextParam(answerTextValues[i]));
            }

            Question question = questionService.get(questionIdParam.intValue());
            List<Answer> answers = answerService.get(answerIds);
            AnswerType answerType = answerService.getAnswerType(answerTypeParam.intValue());

            question.setText(questionTextParam.stringValue());
            for (int i = 0; i < answers.size(); i++) {
                answers.get(i).setText(answerTexts.get(i).stringValue());
                answers.get(i).setType(answerType);
            }
            formService.save(answers, question);

            return AttributeConstants.SUCCESS_RESPONSE_BODY;
        } catch (RequestParamException | RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }
    }

    @RequestMapping(value = {"/delete-answer/{answerId}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteAnswer(@PathVariable int answerId) {
        try {
            answerService.remove(answerId);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return AttributeConstants.SUCCESS_RESPONSE_BODY;
    }

    @RequestMapping(value = {"/delete-question/{questionId}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteQuestion(@PathVariable int questionId) {
        try {
            Question question = questionService.get(questionId);
            formService.remove(question);
            return AttributeConstants.SUCCESS_RESPONSE_BODY;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    //endregion
}