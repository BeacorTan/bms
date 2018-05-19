package com.cl.cm.task.model;

import com.cl.common.model.AuthBase;

import java.util.Date;

/**
 * @author BoSongsh
 * @create 2018-04-23 11:13
 **/
public class TaskSearchConditionVO extends AuthBase {

    private String taskName;

    private String taskType;

    private Integer taskStatus;

    private String beginDate;

    private String endDate;

    // 排序字段
    private String sortName;
    // 排序方式
    private String sortOrder;

    private Date beginTaskBeginDate;

    private Date endTaskBeginDate;

    private Date beginTaskEndDate;

    private Date endTaskEndDate;

    public TaskSearchConditionVO(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskSearchConditionVO() {
    }

    public Date getBeginTaskBeginDate() {
        return beginTaskBeginDate;
    }

    public void setBeginTaskBeginDate(Date beginTaskBeginDate) {
        this.beginTaskBeginDate = beginTaskBeginDate;
    }

    public Date getEndTaskBeginDate() {
        return endTaskBeginDate;
    }

    public void setEndTaskBeginDate(Date endTaskBeginDate) {
        this.endTaskBeginDate = endTaskBeginDate;
    }

    public Date getBeginTaskEndDate() {
        return beginTaskEndDate;
    }

    public void setBeginTaskEndDate(Date beginTaskEndDate) {
        this.beginTaskEndDate = beginTaskEndDate;
    }

    public Date getEndTaskEndDate() {
        return endTaskEndDate;
    }

    public void setEndTaskEndDate(Date endTaskEndDate) {
        this.endTaskEndDate = endTaskEndDate;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
