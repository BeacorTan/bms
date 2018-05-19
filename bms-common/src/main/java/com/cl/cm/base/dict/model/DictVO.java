package com.cl.cm.base.dict.model;

import com.cl.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 数据字典
 *
 * @author BoSongsh
 * @create 2018-03-01 17:10
 **/
@Table(name = "T_SYS_DICTIONARY")
public class DictVO extends BaseModel {

    @Column(name = "CODE")
    private String code;
    @Column(name = "NAME")
    private String name;
    @Column(name = "MARKS")
    private String marks = "";


    public DictVO(String id, int activeFlag) {
        this.id = id;
        this.activeFlag = activeFlag;
    }

    public DictVO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
