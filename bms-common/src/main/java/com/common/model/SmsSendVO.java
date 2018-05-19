package com.common.model;

import java.util.List;

/**
 * @author YH
 * @create 2018-03-13
 */
public class SmsSendVO {

    private String userNo;
    private String mobile;
    private String content;

    private List<TaskVO> tasks;

    public List<TaskVO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskVO> tasks) {
        this.tasks = tasks;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
