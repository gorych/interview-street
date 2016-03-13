package by.gstu.interviewstreet.web.controller;


import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttributeConstants;
import by.gstu.interviewstreet.web.ParameterConstants;
import by.gstu.interviewstreet.web.param.ReqParam;
import by.gstu.interviewstreet.web.param.RequestIdParam;
import by.gstu.interviewstreet.web.param.RequestParamException;
import by.gstu.interviewstreet.web.param.RequestTextParam;
import by.gstu.interviewstreet.web.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class EditorController {

    @Autowired
    FormService formService;

    @Autowired
    AnswerService answerService;

    @Autowired
    QuestionService questionService;

    @Autowired
    InterviewService interviewService;

    @Autowired
    SubdivisionService subdivisionService;

    @Autowired
    EmployeeService employeeService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Set.class, AttributeConstants.POSTS, new CustomCollectionEditor(Set.class) {
            Post post = null;

            @Override
            protected Object convertElement(Object element) {
                try {
                    int id = Integer.parseInt((String) element);
                    post = new Post(id);
                } catch (NumberFormatException e) {
                    return post;
                }
                return post;
            }
        });
    }

    @ModelAttribute(AttributeConstants.INTERVIEWS)
    public List<Interview> loadInterviews() {
        return interviewService.getAll();
    }

    @ModelAttribute(AttributeConstants.SUBDIVISIONS)
    public List<Subdivision> loadSubdivisions() {
        return subdivisionService.getAll();
    }

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String showInterviewList() {
        return "interview-list";
    }

    @RequestMapping(value = {"/designer/{interviewId}"}, method = RequestMethod.GET)
    public String showQuestionsEditor(@PathVariable int interviewId, Model model) {
        List<Form> questionForms = interviewService.getQuestions(interviewId);
        List<List<Form>> answerForms = interviewService.getAnswers(questionForms);

        model.addAttribute(AttributeConstants.QUESTION_FORMS, questionForms);
        model.addAttribute(AttributeConstants.ANSWER_FORMS, answerForms);

        Interview interview = interviewService.get(interviewId);
        model.addAttribute(AttributeConstants.INTERVIEW, interview);

        return "designer";
    }

    @RequestMapping(value = {"/create-question"}, method = RequestMethod.GET)
    @ResponseBody
    public long createQuestion() {
        return questionService.insert();
    }

    @RequestMapping(value = {"/create-answer/{interviewId}/{questionId}"}, method = RequestMethod.GET)
    @ResponseBody
    public long createAnswer(@PathVariable int interviewId, @PathVariable int questionId) {
        Interview interview = interviewService.get(interviewId);
        Question question = questionService.get(questionId);
        Form form = new Form(question, interview);

        return answerService.insert(form);
    }

    @RequestMapping(value = {"/create-interview"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String createInterview(@Valid ExtendUserInterview userInterview) {
        try {
            Interview interview = interviewService.insert(userInterview);
            if (interview == null) {
                return AttributeConstants.ERROR_RESPONSE_BODY;
            }
            return interviewService.getJSON(interview);
        } catch (RequestParamException | RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
        }
    }

    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String loadPosts(@RequestParam String data) {
        Integer[] ids = Parser.parseString(data);
        List<Employee> employees = employeeService.getBySubdivisions(ids);
        return employeeService.getJsonString(employees);
    }

    @RequestMapping(value = {"/load-question/{questionId}"}, method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String loadQuestion(@PathVariable Integer questionId) {
        try {
            return formService.getJSON(questionId);
        } catch (RuntimeException e) {
            return AttributeConstants.EMPTY_BODY;
        }
    }

    @RequestMapping(value = {"/delete-interview"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteInterview(HttpServletRequest req) {
        try {
            String[] strIds = req.getParameterValues("id");

            List<Integer> ids = new ArrayList<>();
            for (String strId : strIds) {
                ids.add(new RequestIdParam(strId).intValue());
            }
            interviewService.remove(ids);
            return AttributeConstants.SUCCESS_RESPONSE_BODY;
        } catch (RequestParamException | RuntimeException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = {"/hide-interview/{interviewId}"}, method = RequestMethod.GET)
    @ResponseBody
    public String hideInterview(@PathVariable int interviewId) {
        try {
            interviewService.hide(interviewId);
            return AttributeConstants.SUCCESS_RESPONSE_BODY;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = {"/edit-interview"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String editInterviewModal(@RequestParam int interviewId) {
        try {
            Interview interview = interviewService.get(interviewId);
            return interviewService.getJSON(interview);
        } catch (RuntimeException e) {
            return AttributeConstants.ERROR_RESPONSE_BODY;
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
}