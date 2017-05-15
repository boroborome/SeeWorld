package com.happy3w.seeworld.controller;

import com.happy3w.seeworld.entity.SystemConfig;
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
    private SystemConfig config = new SystemConfig();
    @RequestMapping(value = "", method = RequestMethod.GET)
    public SystemConfig query() {
        return config;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SystemConfig update(@RequestBody SystemConfig config) {
        this.config = config;
        return config;
    }
}
