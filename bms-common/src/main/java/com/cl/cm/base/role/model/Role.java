package com.cl.cm.base.role.model;

import com.cl.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 角色
 *
 * @author BoSongsh
 * @create 2018-01-12 10:10
 **/
@Table(name = "T_SYS_ROLE")
public class Role extends BaseModel {

    @Column(name = "ROLE_NAME", length = 40, nullable = false, unique = true)
    private String roleName;
    @Column(name = "ROLE_CODE", length = 40, nullable = false, unique = true)
    private String roleCode;
    @Column(name = "REMARKS", length = 40, nullable = false, unique = true)
    private String remarks;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
