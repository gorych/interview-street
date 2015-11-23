package by.gstu.interviewstreet.web.controllers;


import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.helpers.UserInterviewHelper;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttributeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Controller
public class EditorController {

    @Autowired
    FormService formService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    InterviewService interviewService;

    @Autowired
    InterviewTypeService interviewTypeService;

    @Autowired
    SubdivisionService subdivisionService;

    @Autowired
    EmployeeService employeeService;

    @ModelAttribute(AttributeConstants.USER_INTERVIEW_HELPER)
    public UserInterviewHelper loadEmptyUserInterviewBean() {
        return new UserInterviewHelper();
    }

    @ModelAttribute(AttributeConstants.INTERVIEWS)
    public List<Interview> loadInterviews() {
        return interviewService.getAllInterviews();
    }

    @ModelAttribute(AttributeConstants.SUBDIVISIONS)
    public List<Subdivision> loadSubdivisions() {
        return subdivisionService.getAllSubdivisions();
    }

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
                    System.err.println("Element " + element + " is incorrect.");
                    e.printStackTrace();
                }
                return post;
            }
        });
    }

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String goToInterviewList() {
        return "interview-list";
    }

    //TODO Слишком много данных!
    @RequestMapping(value = {"/questions-editor/{interviewId}"}, method = RequestMethod.GET)
    public String goToQuestionsEditor(@PathVariable int interviewId, Model model, HttpServletRequest req) {
        List<Form> questionForms = interviewService.getInterviewQuestions(interviewId);
        if (questionForms != null) {
            List<List<Form>> answerForms = interviewService.getInterviewAnswers(questionForms);
            model.addAttribute(AttributeConstants.QUESTION_FORMS, questionForms);
            model.addAttribute(AttributeConstants.ANSWER_FORMS, answerForms);
        }

        HttpSession session = req.getSession(true);
        Interview interview = interviewService.getInterviewById(interviewId);
        session.setAttribute("interviewId", interviewId);
        session.setAttribute("interview", interview);

        return "questions-editor";
    }

    @RequestMapping(value = {"/create-question"}, method = RequestMethod.GET)
    @ResponseBody
    public long createNewQuestion() {
        return questionService.insertQuestion();
    }

    @RequestMapping(value = {"/create-answer/{interviewId}/{questionId}"}, method = RequestMethod.GET)
    @ResponseBody
    public long createNewAnswer(@PathVariable int interviewId, @PathVariable int questionId) {
        Interview interview = interviewService.getInterviewById(interviewId);
        Question question = questionService.getQuestionById(questionId);
        Form form = new Form(question, interview);

        return answerService.insertAnswer(form);
    }

    @RequestMapping(value = {"/create-interview"}, method = RequestMethod.POST)
    public String createInterview(UserInterviewHelper helper) {
        Set<Post> posts = helper.getPosts();
        if (posts == null) {
            return "redirect:/interview-list";
        }

        List<Integer> ids = new ArrayList<>();
        for (Post post : posts) {
            ids.add(post.getId());
        }

        List<User> users = userService.getUsersByPosts(ids);
        Interview interview = helper.getInterview();

        Calendar calender = Calendar.getInstance();
        java.util.Date utilDate = calender.getTime();
        Date currentDate = new Date(utilDate.getTime());

        int answerTypeId = interview.getType().getId();
        InterviewType type = interviewTypeService.getInterviewTypeById(answerTypeId);

        interview.setPlacementDate(currentDate);
        interview.setType(type);

        interviewService.insertInterview(interview, users);

        return "redirect:/interview-list";
    }

    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String loadPosts(@RequestParam String data) {
        String[] strValues = data.split(",");
        Object[] ids = new Object[strValues.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = Integer.parseInt(strValues[i]);
        }

        List<Employee> employees = employeeService.getEmployeesBySubdivision(ids);
        return employeeService.getJsonString(employees);
    }

    @RequestMapping(value = {"/delete-hide-interview"}, method = RequestMethod.GET)
    public String deleteAndHideInterview(@RequestParam(value = "id") int[] ids,
                                         @RequestParam(value = "operation") String operation) {
        switch (operation) {
            case "delete":
                interviewService.removeInterviews(ids);
                break;
            case "hide":
                //interviewService.hideInterview(id);
                break;
            default:
                //NOP
                break;
        }

        return "redirect:/interview-list";
    }

    @RequestMapping(value = {"/edit-interview"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String editInterviewModal(@RequestParam(value = "interviewId") int interviewId) {
        return interviewService.getJsonString(interviewId);
    }

    @RequestMapping(value = {"/create-new-form"}, method = RequestMethod.POST)
    public String createNewForm(@RequestParam(value = "questionName") String questionText,
                                @RequestParam(value = "answerText") String[] texts,
                                @RequestParam(value = "answerType") Integer[] answerTypeIds,
                                @RequestParam(value = "questionId") int questionId,
                                @RequestParam(value = "answerId") Integer[] answerIds,
                                HttpSession session) {

        Question question = questionService.getQuestionById(questionId);
        List<Answer> answers = answerService.getAnswersByIds(answerIds);
        List<AnswerType> answerTypes = answerService.getAnswerTypesByIds(answerTypeIds);

        question.setText(questionText);
        for (int i = 0; i < answers.size(); i++) {
            answers.get(i).setText(texts[i]);
            answers.get(i).setType(answerTypes.get(i));
        }

        formService.saveForm(answers, question);
        Integer id = (Integer) session.getAttribute("interviewId");

        return "redirect:/questions-editor/" + id;
    }

    @RequestMapping(value = {"/delete-answer/{answerId}"}, method = RequestMethod.GET)
    @ResponseBody
    public int deleteAnswer(@PathVariable(value = "answerId") int answerId){
        answerService.removeAnswer(answerId);
        return 1;
    }

    @RequestMapping(value = {"/delete-question/{questionId}"}, method = RequestMethod.GET)
    @ResponseBody
    public int deleteQuestion(@PathVariable(value = "questionId") int questionId){
        //TODO Удаление вопросов и ответов
        return 1;
    }
}
