package com.task.model;

import com.common.framework.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author BoSongsh
 * @create 2018-04-23 11:13
 **/
@Table(name = "T_TASK")
public class TaskVO extends BaseModel {


    @Column(name = "TASK_NAME")
    private String taskName;
    @Column(name = "TASK_TYPE")
    private String taskType;
    @Column(name = "REMARKS")
    private String remarks;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "BEGIN_DATE")
    private Date beginDate;
    @Column(name = "END_DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
