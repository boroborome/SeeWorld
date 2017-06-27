package com.happy3w.seeworld.elasticsearch;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
//import javaslang.collection.HashSet;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.ResultsMapper;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.geo.CustomGeoModule;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter.serializeAllExcept;

@Configuration
@ConditionalOnClass(ElasticsearchTemplate.class)
@AutoConfigureAfter({ElasticsearchDataAutoConfiguration.class})
public class ElasticsearchResultsMapperAutoConfiguration {
    public static final Set<String> FILTER_OUT_FIELDS = new HashSet<>();
//    HashSet.of(
//            "fieldUpdatedHistoryList",
//            "commentsHistories", "comments",
//            "documentHistories", "document"
//    ).toJavaSet();

    @Bean
    @ConditionalOnMissingBean
    public ResultsMapper resultsMapper(ElasticsearchConverter elasticsearchConverter) {
        return new DefaultResultMapper(elasticsearchConverter.getMappingContext());
    }

    @Bean
    @ConditionalOnMissingBean
    EntityMapper deliveryOrderViewMigrationMapper() {
        return new DeliveryOrderViewMigrationMapper();
    }

    private static class DeliveryOrderViewMigrationMapper implements EntityMapper {
        private ObjectMapper objectMapper;

        public DeliveryOrderViewMigrationMapper() {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            objectMapper.registerModule(new CustomGeoModule());

            setFilter(objectMapper, FILTER_OUT_FIELDS);
        }

        private void setFilter(ObjectMapper objectMapper, java.util.Set<String> filterOutFields) {
            final String migrationFilterId = "migration";
            SimpleFilterProvider filterProvider = new SimpleFilterProvider();
            filterProvider.addFilter(migrationFilterId, serializeAllExcept(filterOutFields));

            SerializationConfig serializationConfig = this.objectMapper.getSerializationConfig();
            AnnotationIntrospector annotationIntrospector = new NopAnnotationIntrospector() {
                @Override
                public Object findFilterId(Annotated ann) {
                    return migrationFilterId;
                }
            };

            objectMapper.setConfig(serializationConfig
                    .withInsertedAnnotationIntrospector(annotationIntrospector)
                    .withFilters(filterProvider)
            );
        }

        @Override
        public String mapToString(Object object) throws IOException {
            return objectMapper.writeValueAsString(object);
        }

        @Override
        public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
            return objectMapper.readValue(source, clazz);
        }
    }

}
