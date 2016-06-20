package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.domain.Question;
import by.gstu.interviewstreet.domain.QuestionType;
import by.gstu.interviewstreet.service.AnswerService;
import by.gstu.interviewstreet.service.QuestionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.CollectionUtils;

/**
 * Тест для проверки БД
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:application-context.xml"
})
@WebAppConfiguration
public class InterviewStreetDbTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Test
    public void test() {
        final int TEST_ID = 1;

        //Получаем записи из БД
        QuestionType questionType = questionService.getType(TEST_ID);
        Question question = questionService.get(TEST_ID);

        //Если результат равен NULL или пустой, будет показано сообщение
        Assert.assertNotNull("Question type should not be null", questionType);

        if (question != null) {
            answerService.addDefaultAnswers(question);
            Assert.assertNotNull("Question answers should not be null", question.getAnswers());
            Assert.assertTrue("List of answers should not be empty",
                               !CollectionUtils.isEmpty(question.getAnswers()));
        }

    }
}
