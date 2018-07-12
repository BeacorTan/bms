package com.base.user.model;

import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "T_SYS_USER_ROLE")
public class UserRoleMap extends BaseModel {

    @Column(name = "ROLE_CODE", length = 40)
    private String roleCode;
    @Column(name = "LOGIN_NAME", length = 40)
    private String loginName;


    public UserRoleMap(String roleCode, String loginName) {
        this.roleCode = roleCode;
        this.loginName = loginName;
    }

    public UserRoleMap(String loginName) {
        this.loginName = loginName;
    }

    public UserRoleMap() {
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
