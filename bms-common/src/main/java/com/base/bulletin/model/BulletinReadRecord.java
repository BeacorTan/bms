package com.base.bulletin.model;

import javax.persistence.Table;

/**
 * 系统公告
 *
 * @author XingyuLi
 */
@Table(name = "T_SYSTEM_BULLETIN")
public class BulletinReadRecord extends SystemBulletin {

    private String readStatus;

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
}
