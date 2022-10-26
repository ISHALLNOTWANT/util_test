package com.ISHALLNOTWANT.dao;

import lombok.Data;

import java.util.Date;

@Data
public class user {
    private Integer id;
    private String name;

    private Date birth;

    public user(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
