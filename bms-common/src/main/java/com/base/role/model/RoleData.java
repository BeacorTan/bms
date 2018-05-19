package com.base.role.model;

import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 角色对应的数据权限
 */
@Table(name = "T_SYS_ROLE_DATA")
public class RoleData extends BaseModel {


    // 控制类型：公司、部门、组
    @Column(length = 60, nullable = false, name = "CTRL_TYPE")
    private String ctrlType;

    // 控制数据
    @Column(length = 60, nullable = false, name = "CTRL_DATA")
    private String ctrlData;

    // 控制数据
    @Column(length = 60, nullable = false, name = "ROLE_CODE")
    private String roleCode;


    public RoleData(String roleCode, String ctrlData) {
        this.roleCode = roleCode;
        this.ctrlData = ctrlData;
    }

    public RoleData(String id) {
        this.id = id;
    }

    public RoleData() {
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getCtrlType() {
        return ctrlType;
    }

    public void setCtrlType(String ctrlType) {
        this.ctrlType = ctrlType;
    }

    public String getCtrlData() {
        return ctrlData;
    }

    public void setCtrlData(String ctrlData) {
        this.ctrlData = ctrlData;
    }
}
