package com.happy3w.seeworld.job;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ysgao on 28/06/2017.
 */
@Component
@RabbitListener(queues = AnalyzeJob.Queue)
public class AnalyzeJob {
    public static final String Queue = "AnalyzeJob";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void receiveMessage(String content) {
        System.out.println(content);
    }
}
