package com.happy3w.seeworld.job;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ysgao on 28/06/2017.
 */
@Component
@RabbitListener(queues = DownloadJob.Queue)
public class DownloadJob {
    public static final String Queue = "DownloadJob";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void receiveMessage(String url) {
        try {
            URL u = new URL(url);
            URLConnection con = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                buf.append(inputLine);
            }
            in.close();
            rabbitTemplate.convertAndSend(AnalyzeJob.Queue, buf.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
