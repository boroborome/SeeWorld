package com.happy3w.seeworld.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by ysgao on 15/05/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "systemconfig", type="systemconfig")
public class SystemConfig {
    public static final int DefaultID = 1;

    @Id
    private int id = DefaultID;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String startUrl;

    @Field(type = FieldType.String)
    private List<String> domainScope;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getDomainScope() {
        return domainScope;
    }

    public void setDomainScope(List<String> domainScope) {
        this.domainScope = domainScope;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }
}
