package com.happy3w.seeworld.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;

/**
 * Created by ysgao on 09/05/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hero {
    private int id;
    private String name;

    public Hero() {
    }

    public Hero(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
