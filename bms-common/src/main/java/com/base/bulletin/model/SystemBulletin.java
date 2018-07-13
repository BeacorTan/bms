package com.base.bulletin.model;

import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 系统公告
 *
 *
 */
@Table(name = "T_SYSTEM_BULLETIN")
public class SystemBulletin extends BaseModel {

    private static final long serialVersionUID = -2181996563953852599L;
    /**
     * 公告标题
     */
    @Column(length = 200, nullable = false, name = "BULLETIN_TITLE")
    private String bulletinTitle;

    /**
     * 公告内容
     */
    @Column(length = 500, nullable = false, name = "BULLETIN_CONTENT")
    private String bulletinContent;

    /**
     * 发布渠道
     */
    @Column(length = 200, nullable = false, name = "RELEASE_CHANNEL")
    private String releaseChannel;


    /**
     * 发布人
     */
    @Column(length = 200, nullable = false, name = "AUTHOR")
    private String author;


    public SystemBulletin() {

    }

    public SystemBulletin(String id) {
        this.id = id;
    }

    public String getBulletinTitle() {
        return bulletinTitle;
    }

    public void setBulletinTitle(String bulletinTitle) {
        this.bulletinTitle = bulletinTitle;
    }

    public String getBulletinContent() {
        return bulletinContent;
    }

    public void setBulletinContent(String bulletinContent) {
        this.bulletinContent = bulletinContent;
    }

    public String getReleaseChannel() {
        return releaseChannel;
    }

    public void setReleaseChannel(String releaseChannel) {
        this.releaseChannel = releaseChannel;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
