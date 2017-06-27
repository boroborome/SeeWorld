package com.happy3w.seeworld.repository;

import com.happy3w.seeworld.entity.SystemConfig;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by ysgao on 16/05/2017.
 */
public interface SystemConfigRepository extends ElasticsearchRepository<SystemConfig, Integer> {

    default SystemConfig load() {

        return exists(SystemConfig.DefaultID) ? findOne(SystemConfig.DefaultID) : new SystemConfig();
    }
}