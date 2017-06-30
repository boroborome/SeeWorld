package com.happy3w.seeworld.task;

import com.happy3w.seeworld.SeeWorldApplication;
import com.happy3w.seeworld.job.AnalyzeJob;
import com.happy3w.seeworld.job.DispatchJob;
import com.happy3w.seeworld.job.SaveContentJob;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by ysgao on 28/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeeWorldApplication.class)
public class AnalyzeJobTest {

    @Autowired
    private AnalyzeJob analyzeJob;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    public void should_analyze_success() {
        String content = "<html><body><a href=\"http://www.baidu.com\">abc</a></body></html>";
        analyzeJob.receiveMessage(content);

        ArgumentCaptor<String> captorQueue = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorContent = ArgumentCaptor.forClass(String.class);
        Mockito.verify(rabbitTemplate, Mockito.times(2))
                .convertAndSend(captorQueue.capture(), captorContent.capture());

        verifyParameter(new ArgumentCaptor[]{captorQueue, captorContent},
                new Object[][]{
                        {DispatchJob.Queue, "http://www.baidu.com"},
                        {SaveContentJob.Queue, content},
                });
    }

    private void verifyParameter(ArgumentCaptor[] argumentCaptors, Object[][] expectValues) {
        Assert.assertNotNull(argumentCaptors);
        Assert.assertNotNull(expectValues);
        Assert.assertTrue(argumentCaptors.length > 0);
        Assert.assertTrue(expectValues.length > 0);

        for (int row = 0, len = expectValues.length; row < len; row++) {
            Object[] values = expectValues[row];
            Assert.assertEquals(argumentCaptors.length, values.length);

            for (int column = 0, columnCount = argumentCaptors.length; column < columnCount; column++) {
                Assert.assertEquals(values[column], argumentCaptors[column].getAllValues().get(row));
            }
        }
    }
}
