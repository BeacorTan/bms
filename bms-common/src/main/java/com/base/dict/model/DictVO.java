package com.base.dict.model;

import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "T_SYS_DICTIONARY")
public class DictVO extends BaseModel {

    @Column(name = "CODE")
    private String code;
    @Column(name = "NAME")
    private String name;
    @Column(name = "MARKS")
    private String marks;


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
