package by.gstu.interviewstreet.dao.impl;

import by.gstu.interviewstreet.dao.AnswerTypeDAO;
import by.gstu.interviewstreet.domain.AnswerType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Тест для проверки БД
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/application-context.xml"
})
public class InterviewStreetDbTest {

    @Autowired
    private AnswerTypeDAO answerTypeDAO;

    @Test
    public void test() {
        List<AnswerType> types = answerTypeDAO.getAll();

        Assert.isTrue(types.isEmpty(), "Types are empty");
        Assert.isNull(types, "Types are null");
    }

}
