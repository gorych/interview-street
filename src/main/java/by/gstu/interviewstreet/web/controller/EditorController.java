package by.gstu.interviewstreet.web.controller;


import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.AttributeConstants;
import by.gstu.interviewstreet.web.util.ControllerUtils;
import by.gstu.interviewstreet.web.util.JSONParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@Secured(UserRoleConstants.EDITOR)
public class EditorController {

    @Autowired
    public UserService userService;

    @Autowired
    public AnswerService answerService;

    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public QuestionService questionService;

    @Autowired
    public InterviewService interviewService;

    @Autowired
    public SubdivisionService subdivisionService;

    @Autowired
    public UserInterviewService userInterviewService;

    @ResponseBody
    @RequestMapping(value = {"/hide-chip"}, method = RequestMethod.GET)
    public ResponseEntity<String> hideChip(HttpSession session) {
        session.setAttribute(AttributeConstants.CHIP, false);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = {"/interview-list"}, method = RequestMethod.GET)
    public String showInterviewList(Model model) {
        model.addAttribute(AttributeConstants.INTERVIEWS, interviewService.getAll());
        model.addAttribute(AttributeConstants.SUBDIVISIONS, subdivisionService.getAll());

        return "interview-list";
    }

    @ResponseBody
    @RequestMapping(value = {"/load-posts"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> loadPosts(@RequestBody String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> subdivisionIds = JSONParser.convertJsonElementToObject(jsonArray, type);
        List<Employee> employees = employeeService.getBySubdivisions(subdivisionIds);

        String jsonData = JSONParser.convertObjectToJsonString(employees);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/save-interview"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> processAddInterviewForm(@RequestBody String data) {
        JsonArray jsonArray = JSONParser.convertJsonStringToJsonArray(data);

        JsonElement interviewElement = jsonArray.get(0);
        JsonArray idsArray = jsonArray.get(1).getAsJsonArray();

        Interview interview = JSONParser.convertJsonElementToObject(interviewElement, Interview.class);
        interview = interviewService.saveOrUpdate(interview);

        if (interview.getType().isOpen()) {
            Integer[] postIds = JSONParser.convertJsonElementToObject(idsArray, Integer[].class);
            userInterviewService.addInterviewToUserByPost(interview, postIds);
        }

        String jsonData = JSONParser.convertObjectToJsonString(interview.getId());

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/delete-interview"}, method = RequestMethod.POST)
    public ResponseEntity<String> deleteInterview(@RequestParam String data) {
        Interview interview = JSONParser.convertJsonStringToObject(data, Interview.class);
        interviewService.remove(interview);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/lock-interview/{interviewId}"}, method = RequestMethod.GET)
    public ResponseEntity<String> lockOrUnlockInterview(@PathVariable int interviewId) {
        interviewService.lockOrUnlock(interviewId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/load-card-values"}, method = RequestMethod.GET, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> loadValuesForCard(@RequestParam int interviewId) {
        Map<String, Object> valueMap = interviewService.getValueMapForCard(interviewId);
        String jsonData = JSONParser.convertObjectToJsonString(valueMap);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{hash}/designer"}, method = RequestMethod.GET)
    public String showDesigner(@PathVariable String hash, Model model) {
        Interview interview = interviewService.get(hash);
        if (interview == null) {
            return "404";
        }

        List<Question> questions = questionService.getAllOrderByNumber(hash);

        model.addAttribute(AttributeConstants.INTERVIEW, interview);
        model.addAttribute(AttributeConstants.QUESTIONS, questions);

        return "designer";
    }

    @ResponseBody
    @RequestMapping(value = {"/designer/add-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> addQuestion(String hash, int answerTypeId, int number) {
        try {
            //TODO add by question type
            AnswerType answerType = answerService.getAnswerType(answerTypeId);
            Interview interview = interviewService.get(hash);

            if (interview == null || answerType == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Question question = questionService.addQuestion(interview, answerType, number);
            Map<String, Object> valueMap = questionService.getValueMapForQuestionForm(question, answerType);

            String jsonData = JSONParser.convertObjectToJsonString(valueMap);

            return new ResponseEntity<>(jsonData, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/designer/del-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> removeQuestion(@RequestParam String hash, @RequestParam int id) {
        try {
            Interview interview = interviewService.get(hash);
            Question question = questionService.get(id);

            Set<Question> questions = interview.getQuestions();
            if (!questions.contains(question)) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            questionService.remove(question);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/designer/move-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> moveQuestion(@RequestParam int id, @RequestParam int number) {
        try {
            questionService.move(id, number);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/designer/duplicate-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> duplicateQuestion(@RequestParam int id) {
        try {
            Question question = questionService.get(id);

            Interview interview = question.getInterview();
            Integer nextNumber = question.getNumber() + 1;
            //TODO Add by question type
            AnswerType answerType = question.getAnswers().get(0).getType();

            Question duplicated = questionService.addQuestion(interview, answerType, nextNumber);
            Map<String, Object> valueMap = questionService.getValueMapForDuplicateQuestionForm(question, duplicated, answerType);

            String jsonData = JSONParser.convertObjectToJsonString(valueMap);

            return new ResponseEntity<>(jsonData, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/designer/add-answer"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> addAnswer(String hash, int questId, @RequestParam(required = false) boolean textType) {
        try {
            Interview interview = interviewService.get(hash);
            Question question = questionService.get(questId);
            AnswerType answerType = question.getAnswers().get(0).getType();

            Set<Question> questions = interview.getQuestions();
            if (!questions.contains(question)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Answer answer;
            if (textType && ControllerUtils.notExistTextAnswer(questions)) {
                answer = answerService.addDefaultTextAnswer(question);
            } else {
                answer = answerService.addDefaultAnswer(answerType, question);
            }

            String jsonData = JSONParser.convertObjectToJsonString(answer);

            return new ResponseEntity<>(jsonData, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = {"/designer/del-answer"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> removeAnswer(String hash, int questId, int answerId) {
        try {
            Interview interview = interviewService.get(hash);
            Question question = questionService.get(questId);
            Answer answer = answerService.get(answerId);

            Set<Question> questions = interview.getQuestions();
            if (!questions.contains(question)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            final int MIN_ANSWER_COUNT = 2;
            List<Answer> answers = question.getAnswers();

            if (answers.size() <= MIN_ANSWER_COUNT || !answers.contains(answer)) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            answerService.remove(answer);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}