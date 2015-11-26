package by.gstu.interviewstreet.web.controllers;


import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttributeConstants;
import by.gstu.interviewstreet.web.utils.MessageUtils;
import by.gstu.interviewstreet.web.utils.ParamsValidator;
import by.gstu.interviewstreet.web.utils.Parser;
import by.gstu.interviewstreet.web.utils.exceptions.FormParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class EditorController {

    @Autowired
    MessageUtils messenger;

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
                    System.err.println("Элемент " + element + " не может быть преобразован к числу.");
                    e.printStackTrace();
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

    @RequestMapping(value = {"/questions-editor/{interviewId}"}, method = RequestMethod.GET)
    public String showQuestionsEditor(@PathVariable int interviewId, Model model) {
        List<Form> questionForms = interviewService.getQuestions(interviewId);
        List<List<Form>> answerForms = interviewService.getAnswers(questionForms);

        model.addAttribute(AttributeConstants.QUESTION_FORMS, questionForms);
        model.addAttribute(AttributeConstants.ANSWER_FORMS, answerForms);

        Interview interview = interviewService.get(interviewId);
        model.addAttribute(AttributeConstants.INTERVIEW, interview);

        return "questions-editor";
    }

    @RequestMapping(value = {"/create-question"}, method = RequestMethod.GET)
    @ResponseBody
    public long createQuestion() {
        return questionService.insert();
    }

    @RequestMapping(value = {"/create-answer/{interviewId}/{questionId}"}, method = RequestMethod.GET)
    @ResponseBody
    public long createNewAnswer(@PathVariable int interviewId, @PathVariable int questionId) {
        Interview interview = interviewService.get(interviewId);
        Question question = questionService.get(questionId);
        Form form = new Form(question, interview);

        return answerService.insert(form);
    }

    @RequestMapping(value = {"/create-interview"}, method = RequestMethod.POST)
    @ResponseBody
    public String createInterview(@Valid ExtendUserInterview userInterview, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return messenger.getMessageFromBindingResult(bindingResult);
        }
        try {
            interviewService.insert(userInterview);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return AttributeConstants.SUCCESS_RESPONSE_BODY;
    }

    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String loadPosts(@RequestParam String data) {
        Integer[] ids = Parser.parseString(data);
        List<Employee> employees = employeeService.getBySubdivisions(ids);
        return employeeService.getJsonString(employees);
    }

    @RequestMapping(value = {"/delete-interview"}, method = RequestMethod.GET)
    public String deleteInterview(@RequestParam(value = "id") int[] ids) {
        try {
            interviewService.remove(ids);
            return "redirect:/interview-list";
        } catch (RuntimeException e) {
            return "redirect:/interview-list";
        }
    }

    @RequestMapping(value = {"/hide-interview/{interviewId}"}, method = RequestMethod.GET)
    @ResponseBody
    public String hideInterview(@PathVariable(value = "interviewId") int id) {
        try {
            interviewService.hide(id);
            return AttributeConstants.SUCCESS_RESPONSE_BODY;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = {"/edit-interview"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String editInterviewModal(@RequestParam(value = "interviewId") int interviewId) {
        return interviewService.getJsonString(interviewId);
    }

    @RequestMapping(value = {"/create-new-form"}, method = RequestMethod.POST)
    public String createForm(@RequestParam(value = "questionText", required = false) String questionText,
                             @RequestParam(value = "answerText", required = false) String[] answerTexts,
                             @RequestParam(value = "typeId", required = false) Integer typeId,
                             @RequestParam(value = "questionId", required = false) Integer questionId,
                             @RequestParam(value = "answerId", required = false) Integer[] ansIds,
                             HttpSession session) {
        Integer id = (Integer) session.getAttribute(AttributeConstants.INTERVIEW_ID);
        try {
            ParamsValidator.checkQuestion(questionText);
            ParamsValidator.checkAnswers(answerTexts);

            Question question = questionService.get(questionId);
            List<Answer> answers = answerService.get(ansIds);
            AnswerType answerType = answerService.getAnswerType(typeId);

            question.setText(questionText);
            for (int i = 0; i < answers.size(); i++) {
                answers.get(i).setText(answerTexts[i]);
                answers.get(i).setType(answerType);
            }
            formService.save(answers, question);

            return "redirect:/questions-editor/" + id;
        } catch (FormParamException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return "redirect:/questions-editor/" + id;
        }
    }

    @RequestMapping(value = {"/delete-answer/{answerId}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteAnswer(@PathVariable(value = "answerId") int answerId) {
        try {
            answerService.remove(answerId);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return AttributeConstants.SUCCESS_RESPONSE_BODY;
    }

    @RequestMapping(value = {"/delete-question/{questionId}"}, method = RequestMethod.GET)
    @ResponseBody
    public String deleteQuestion(@PathVariable(value = "questionId") int questionId) {
        try {
            Question question = questionService.get(questionId);
            formService.remove(question);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        return AttributeConstants.SUCCESS_RESPONSE_BODY;
    }
}