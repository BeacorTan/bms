package com.task.model;

/**
 * @author BoSongsh
 * @create 2018-04-23 11:13
 **/
public class TaskDistributionRecordVO extends TaskVO {

    private String userNo;

    private String empName;

    private Integer taskStatus;

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
