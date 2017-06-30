package com.happy3w.seeworld.task;

import com.happy3w.seeworld.SeeWorldApplication;
import com.happy3w.seeworld.job.AnalyzeJob;
import com.happy3w.seeworld.job.DownloadJob;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by ysgao on 28/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeeWorldApplication.class)
//@TestConfiguration
//@MockBean(RabbitTemplate.class)
public class DownloadTaskTest {

    @Autowired
    private DownloadJob downloadJob;

//    @Autowired
//    @SpyBean
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_download_success() {
        downloadJob.receiveMessage("http://www.sina.com.cn");

        ArgumentCaptor<String> captorQueue = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captorContent = ArgumentCaptor.forClass(String.class);
        Mockito.verify(rabbitTemplate)
                .convertAndSend(captorQueue.capture(), captorContent.capture());

        Assert.assertEquals(AnalyzeJob.Queue, captorQueue.getValue());
        Assert.assertNotNull(captorContent.getValue());
        Assert.assertTrue(captorContent.getValue().length() > 0);
    }
}
