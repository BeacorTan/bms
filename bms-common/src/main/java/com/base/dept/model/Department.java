package com.base.dept.model;


import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "T_SYS_DEPT")
public class Department extends BaseModel {

    /**
     * 部门名称
     */
    @Column(name = "DEPT_NAME")
    private String deptName;

    /**
     * 部门编码
     */
    @Column(name = "DEPT_CODE")
    private String deptCode;


    /**
     * 部门类型
     */
    @Column(name = "DEPT_TYPE")
    private String deptType;

    // 全节点名
    @Column(name = "TREE_NAMES")
    private String treeNames;

    /**
     * 备注信息
     */
    @Column(name = "REMARKS")
    private String remarks;

    // 是否最末级 0:否 1：是
    @Column(name = "TREE_LEAF")
    private String treeLeaf;

    /**
     * 部门领导
     */
    @Column(name = "LEADER")
    private String leader;

    // 层次级别
    @Column(name = "TREE_LEVEL")
    private Integer treeLevel;

    @Column(name = "PARENT_CODES")
    private String parentCodes;

    /**
     * 排序号
     */
    @Column(name = "TREE_SORT")
    private Integer treeSort;

    /**
     * 父部门
     */
    @Column(name = "PARENT_CODE")
    private String parentCode;


    /**
     * 电话
     */
    @Column(name = "PHONE")
    private String phone;

    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * 邮编
     */
    @Column(name = "ZIP_CODE")
    private String zipCode;

    /**
     * 邮箱
     */
    @Column(name = "EMAIL")
    private String email;

    public Department(String deptCode, Integer activeFlag) {
        this.activeFlag = activeFlag;
        this.deptCode = deptCode;
    }

    public Department(String deptCode, String treeLeaf) {
        this.treeLeaf = treeLeaf;
        this.deptCode = deptCode;
    }

    public Department() {
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getTreeNames() {
        return treeNames;
    }

    public void setTreeNames(String treeNames) {
        this.treeNames = treeNames;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTreeLeaf() {
        return treeLeaf;
    }

    public void setTreeLeaf(String treeLeaf) {
        this.treeLeaf = treeLeaf;
    }

    public Integer getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getParentCodes() {
        return parentCodes;
    }

    public void setParentCodes(String parentCodes) {
        this.parentCodes = parentCodes;
    }

    public Integer getTreeSort() {
        return treeSort;
    }

    public void setTreeSort(Integer treeSort) {
        this.treeSort = treeSort;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}