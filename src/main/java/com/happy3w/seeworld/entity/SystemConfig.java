package com.happy3w.seeworld.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by ysgao on 15/05/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemConfig {
    private String startUrl;
    private List<String> domainScope;

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
