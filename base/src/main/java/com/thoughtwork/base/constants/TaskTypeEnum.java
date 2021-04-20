package com.thoughtwork.base.constants;

/**
 * Time :2021/3/17
 * Author:xbj
 * Description :
 */
public enum TaskTypeEnum {
    TASK_1(1,"网点业务量信息维护任务"),
    TASK_2(2,"网点业务员信息维护任务"),
    TASK_3(3,"网点车辆信息维护任务"),
    TASK_4(4,"网点设备信息维护任务"),
    TASK_5(5,"驿站业务量信息维护任务");
    private int taskCode;
    private String taskName;

    TaskTypeEnum(int taskCode, String taskName) {
        this.taskCode = taskCode;
        this.taskName = taskName;
    }

    public int getTaskCode() {
        return taskCode;
    }

    public String getTaskName() {
        return taskName;
    }
}
