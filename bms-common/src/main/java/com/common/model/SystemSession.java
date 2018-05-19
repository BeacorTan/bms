package com.common.model;

import com.common.framework.base.BaseModel;

import javax.persistence.Table;
import java.util.Date;

/**
 * session类
 */
@Table(name = "T_SYS_SESSION")
public class SystemSession extends BaseModel {

    private String ip;

    private Date lastAccessTime;

    private String sessionId;

    private String sessionValue;

    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionValue() {
        return sessionValue;
    }

    public void setSessionValue(String sessionValue) {
        this.sessionValue = sessionValue;
    }
}
