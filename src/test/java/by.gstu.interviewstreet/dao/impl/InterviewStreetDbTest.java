package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.domain.Interview;
import by.gstu.interviewstreet.service.AnswerService;
import by.gstu.interviewstreet.service.InterviewService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

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
    private AnswerService answerService;

    @Autowired
    private InterviewService interviewService;

    @Test
    public void test() {
        final int TEST_ID = 5;

        /***
         * Получаем записи из БД
         */
        Answer answer = answerService.get(TEST_ID);
        List<Interview> interviews = interviewService.getAll();

        /***
         * Если результат равен NULL или пустой, будет показано сообщение
         */
        Assert.assertNotNull("Answer should not be null", answer);
        Assert.assertNotNull("Interview list should not be null", interviews);
        Assert.assertTrue("Interview list should not be empty", interviews.size() > 0);
    }
}
