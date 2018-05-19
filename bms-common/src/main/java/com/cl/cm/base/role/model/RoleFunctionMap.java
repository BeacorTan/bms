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
@Table(name = "T_SYS_ROLE_FUNCTION")
public class RoleFunctionMap extends BaseModel {

    @Column(name = "ROLE_CODE", length = 40)
    private String roleCode;
    @Column(name = "FUNCTION_CODE", length = 40)
    private String functionCode;
//    @Column(name = "REMARKS", length = 200)
//    private String remarks;


    public RoleFunctionMap(String roleCode, String functionCode) {
        this.roleCode = roleCode;
        this.functionCode = functionCode;
    }
    public RoleFunctionMap(String roleCode) {
        this.roleCode = roleCode;
    }

    public RoleFunctionMap() {
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
