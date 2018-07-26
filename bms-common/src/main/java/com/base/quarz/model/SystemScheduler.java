package com.base.quarz.model;

import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 调度器bean
 */
@Table(name = "T_SYS_SCHEDULER")
public class SystemScheduler extends BaseModel {

    /**
     * 任务名称
     */
    @Column(name = "JOB_NAME")
    private String jobName;

    /**
     * 任务类
     */
    @Column(name = "JOB_CLASS")
    private String jobClass;
    /**
     * 表达式
     */
    @Column(name = "CRON")
    private String cron;
    /**
     * 启动状态 1：启动 0：停止
     */
    @Column(name = "IS_START")
    private Integer start;

    /**
     * 任务参数
     */
    @Column(name = "JOB_PARAMS")
    private String jobParams;
    /**
     * 任务说明
     */
    @Column(name = "NOTES")
    private String notes;


    /**
     * 手动触发路径
     */
    @Column(name = "URL")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass == null ? null : jobClass.trim();
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getJobParams() {
        return jobParams;
    }

    public void setJobParams(String jobParams) {
        this.jobParams = jobParams == null ? null : jobParams.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

}
