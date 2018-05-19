package com.cl.cm.task.model;

import com.cl.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author BoSongsh
 * @create 2018-04-23 11:13
 **/
@Table(name = "T_TASK_DISTRIBUTION")
public class TaskDistributionVO extends BaseModel {

    @Column(name = "TASK_ID")
    private String taskId;
    @Column(name = "USER_NO")
    private String userNo;
    @Column(name = "TASK_STATUS")
    private Integer taskStatus;

    @Column(name = "EMP_CODE")
    private String empCode;

    public TaskDistributionVO(String taskId, Integer taskStatus) {
        this.taskStatus = taskStatus;
        this.taskId = taskId;
    }

    public TaskDistributionVO() {
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }
}
