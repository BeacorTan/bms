package com.base.bulletin.model;

import com.common.model.SearchConditionBase;

import javax.persistence.Column;


public class SystemBulletinSearchCondition extends SearchConditionBase {

    private static final long serialVersionUID = -2181996563953852599L;
    /**
     * 公告标题
     */
    @Column(length = 200, nullable = false, name = "BULLETIN_TITLE")
    private String bulletinTitle;

    public String getBulletinTitle() {
        return bulletinTitle;
    }

    public void setBulletinTitle(String bulletinTitle) {
        this.bulletinTitle = bulletinTitle;
    }
}
