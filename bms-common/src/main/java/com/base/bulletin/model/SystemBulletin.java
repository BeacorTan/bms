package com.base.bulletin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.common.framework.base.BaseModel;

/**
 * 系统公告
 *
 * @author XingyuLi
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
     * 开始时间
     */
    @Column(name = "BEGIN_DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date beginDate;

    /**
     * 结束时间
     */
    @Column(name = "END_DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;

    private String beginDateText;
    private String endDateText;

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

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBeginDateText() {
        return beginDateText;
    }

    public void setBeginDateText(String beginDateText) {
        this.beginDateText = beginDateText;
    }

    public String getEndDateText() {
        return endDateText;
    }

    public void setEndDateText(String endDateText) {
        this.endDateText = endDateText;
    }


}
