package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.domain.Answer;
import by.gstu.interviewstreet.service.AnswerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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

    @Test
    public void test() {
        Answer answer = answerService.get(50000);
        Assert.assertNotNull("Answer is null", answer);
    }

}
