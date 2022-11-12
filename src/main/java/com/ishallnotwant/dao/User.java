package com.ishallnotwant.dao;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String name;

    private Date birth;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
