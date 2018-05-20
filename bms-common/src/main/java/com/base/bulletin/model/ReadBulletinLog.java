package com.base.bulletin.model;

import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 公告阅读记录
 *
 *
 */
@Table(name = "T_READ_LOG")
public class ReadBulletinLog extends BaseModel {

    private static final long serialVersionUID = 6311154890629289487L;

    @Column(length = 200, nullable = false, name = "BULLETIN_ID")
    private String bulletinId;

    @Column(length = 500, nullable = false, name = "READ_STATUS")
    private String readStatus;

    @Column(length = 200, nullable = false, name = "LOGIN_NAME")
    private String loginName;

    public ReadBulletinLog(String loginName, String bulletinId) {
        this.loginName = loginName;
        this.bulletinId = bulletinId;
    }

    public ReadBulletinLog() {
    }


    public String getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(String bulletinId) {
        this.bulletinId = bulletinId;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
