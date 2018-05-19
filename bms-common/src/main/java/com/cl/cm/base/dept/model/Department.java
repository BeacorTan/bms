package com.cl.cm.base.dept.model;


import com.cl.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "T_SYS_DEPT")
public class Department extends BaseModel{
    /**部门领导*/
    @Column(name="DEP_LEADER")
    private String depLeader;

    /**部门名称*/
    @Column(name="DEP_NAME")
    private String depName;

    /**部门类型*/
    @Column(name="DEP_TYPE")
    private String depType;

    /**描述*/
    @Column(name="DESCRIPTION")
    private String description;

    /**排序号*/
    @Column(name="ORDER_NUMBER")
    private Integer orderNumber;

    /**父部门*/
    @Column(name="PARENT_ID")
    private String parentId;

    /**部门编码*/
    @Column(name = "DEP_CODE")
    private String depCode;

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepLeader() {
        return depLeader;
    }

    public void setDepLeader(String depLeader) {
        this.depLeader = depLeader == null ? null : depLeader.trim();
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public String getDepType() {
        return depType;
    }

    public void setDepType(String depType) {
        this.depType = depType == null ? null : depType.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }
}