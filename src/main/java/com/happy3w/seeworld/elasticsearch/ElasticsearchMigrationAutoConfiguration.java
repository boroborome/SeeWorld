package com.happy3w.seeworld.elasticsearch;

import com.happy3w.seeworld.entity.SystemConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

@Configuration
@ConditionalOnClass(ElasticsearchTemplate.class)
@AutoConfigureAfter({ElasticsearchAutoConfiguration.class})
@EnableElasticsearchRepositories("com.happy3w.seeworld.repository")
public class ElasticsearchMigrationAutoConfiguration {

    private final ElasticsearchTemplate elasticsearchTemplate;


    public ElasticsearchMigrationAutoConfiguration(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @PostConstruct
    public void migrateIndex() {
        Stream.of(
                SystemConfig.class)
                .forEach(clz -> {
                    elasticsearchTemplate.createIndex(clz);
                    elasticsearchTemplate.putMapping(clz);
                });
    }
}
