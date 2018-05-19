package com.cl.cm.base.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author BoSongsh
 * @create 2018-01-24 11:51
 **/
public class SearchConditionBase {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date beginDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;

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
}
