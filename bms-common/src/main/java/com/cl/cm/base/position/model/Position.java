package com.cl.cm.base.position.model;

import com.cl.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 职位
 */
@Table(name = "T_SYS_POSITION")
public class Position extends BaseModel {


    // 职位编码
    @Column(length = 60, nullable = false, name = "POSITION_CODE")
    private String positionCode;

    // 职位名称
    @Column(length = 60, nullable = false, name = "POSITION_NAME")
    private String positionName;

    // 备注信息
    @Column(length = 255, name = "MARKS")
    private String marks;

    public Position(String id) {
        this.id = id;
    }

    public Position() {
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
