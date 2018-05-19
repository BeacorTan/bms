package com.base.role.model;

import com.base.dept.model.Department;

import java.util.List;

/**
 * 角色vo
 *
 * @author BoSongsh
 * @create 2018-01-15 9:50
 **/
public class RoleVO extends Role {

    List<String> functionCodes;

    // 授权数据
    List<Department> authData;

    public List<Department> getAuthData() {
        return authData;
    }

    public void setAuthData(List<Department> authData) {
        this.authData = authData;
    }

    public List<String> getFunctionCodes() {
        return functionCodes;
    }

    public void setFunctionCodes(List<String> functionCodes) {
        this.functionCodes = functionCodes;
    }
}
