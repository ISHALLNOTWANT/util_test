package com.ishallnotwant.dao;

import lombok.Data;

@Data
public class task {

    private int taskId;

    private String taskName;

    //合格 不合格
    private String taskResult;

    private String executeName;

    public task(int taskId,String taskName,String taskResult,String executeName){
        this.taskId=taskId;
        this.taskName=taskName;
        this.taskResult=taskResult;
        this.executeName=executeName;
    }

}
