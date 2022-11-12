package com.ishallnotwant.dao;

import lombok.Data;

@Data
public class Task {

    private int taskId;

    private String taskName;

    //合格 不合格
    private String taskResult;

    private String executeName;

    public Task(int taskId, String taskName, String taskResult, String executeName){
        this.taskId=taskId;
        this.taskName=taskName;
        this.taskResult=taskResult;
        this.executeName=executeName;
    }

}
