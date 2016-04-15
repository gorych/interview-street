package by.gstu.interviewstreet.web.controller.action;

import by.gstu.interviewstreet.domain.*;
import by.gstu.interviewstreet.security.UserRoleConstants;
import by.gstu.interviewstreet.service.*;
import by.gstu.interviewstreet.web.util.AnswerValidator;
import by.gstu.interviewstreet.web.util.ControllerUtils;
import by.gstu.interviewstreet.web.util.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/designer")
@Secured(UserRoleConstants.EDITOR)
public class DesignerActionsController {

    @Autowired
    AnswerValidator validator;

    @Autowired
    AnswerService answerService;

    @Autowired
    QuestionService questionService;

    @Autowired
    InterviewService interviewService;

    @ResponseBody
    @RequestMapping(value = {"/add-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> addQuestion(String hash, int questTypeId, int number) {
        QuestionType questionType = questionService.getType(questTypeId);
        Interview interview = interviewService.get(hash);

        if (interview == null || questionType == null) {
            return new ResponseEntity<>(
                    "Error getting interview or question type. " +
                    "Interview hash = " + hash + ". question type id = " + questTypeId,
                    HttpStatus.BAD_REQUEST
            );
        }

        Question question = questionService.addDefaultQuestion(interview, questionType, number);
        List<Answer> answers = answerService.addDefaultAnswers(question);

        question.setAnswers(answers);
        String jsonData = JSONParser.convertObjectToJsonString(question);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/del-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> removeQuestion(String hash, int id) {
        Question question = questionService.get(hash, id);
        questionService.remove(question);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/move-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> moveQuestion(int id, int number) {
        questionService.move(id, number);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/duplicate-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> duplicateQuestion(int id) {
        Question question = questionService.get(id);

        Interview interview = question.getInterview();
        Integer nextNumber = question.getNumber() + 1;
        QuestionType questionType = question.getType();

        Question duplicated = questionService.addDefaultQuestion(interview, questionType, nextNumber);
        List<Answer> answers = answerService.duplicateAnswers(question, duplicated);

        duplicated.setAnswers(answers);
        String jsonData = JSONParser.convertObjectToJsonString(duplicated);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/save-question"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> saveQuestion(String hash, int questId, String text) {
        Question question = questionService.get(hash, questId);

        question.setText(text);
        questionService.saveOrUpdate(question);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/add-answer"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> addAnswer(String hash, int questId, @RequestParam(required = false) boolean textType) {
        Interview interview = interviewService.get(hash);
        Question question = questionService.get(questId);
        AnswerType answerType = question.getAnswers().get(0).getType();

        Set<Question> questions = interview.getQuestions();
        if (!questions.contains(question)) {
            return new ResponseEntity<>(
                    "Question with id = " + questId + " not exist in the interview with hash = " + hash,
                    HttpStatus.BAD_REQUEST
            );
        }

        Answer answer;
        if (textType && ControllerUtils.notExistTextAnswer(questions)) {
            answer = answerService.addDefaultTextAnswer(question);
        } else {
            answer = answerService.addDefaultAnswer(answerType, question);
        }

        String jsonData = JSONParser.convertObjectToJsonString(answer);

        return new ResponseEntity<>(jsonData, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/del-answer"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> removeAnswer(String hash, int questId, int answerId) {
        Question question = questionService.get(hash, questId);
        Answer answer = answerService.get(question, answerId);

        final int MIN_ANSWER_COUNT = 2;
        List<Answer> answers = question.getAnswers();

        if (answers.size() <= MIN_ANSWER_COUNT) {
            return new ResponseEntity<>(
                    "Error deleting the answer. The question must have at least two responses. Current answers size = " + answers.size(),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }

        answerService.remove(answer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = {"/save-answer"}, method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> saveAnswer(int questId, int answerId, String text) {
        Question question = questionService.get(questId);
        Answer answer = answerService.get(question, answerId);

        answer.setText(text);

        BindException bindException = new BindException(answer, "answer");
        validator.validate(answer, bindException);

        if (bindException.hasErrors()) {
            return new ResponseEntity<>("Value = " + text + " is invalid.", HttpStatus.NOT_ACCEPTABLE);
        }

        answerService.saveOrUpdate(answer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
