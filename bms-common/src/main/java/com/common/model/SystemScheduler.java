package com.common.model;

import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 调度器bean
 */
@Table(name="T_SYS_SCHEDULER")
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
	 * 是否启动
     */
	@Column(name = "IS_START")
	private String isStart;

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

	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart == null ? null : isStart.trim();
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
