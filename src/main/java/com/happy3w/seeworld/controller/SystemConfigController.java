package com.happy3w.seeworld.controller;

import com.happy3w.seeworld.entity.SystemConfig;
import com.happy3w.seeworld.job.DownloadJob;
import com.happy3w.seeworld.repository.SystemConfigRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ysgao on 15/05/2017.
 */
@RestController
@RequestMapping("/api/system-config")
public class SystemConfigController {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public SystemConfig query() {
        return systemConfigRepository.load();
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SystemConfig update(@RequestBody SystemConfig config) {
        config.setId(SystemConfig.DefaultID);
        systemConfigRepository.save(config);
        rabbitTemplate.convertAndSend(DownloadJob.Queue, config.getStartUrl());
        return config;
    }
}
