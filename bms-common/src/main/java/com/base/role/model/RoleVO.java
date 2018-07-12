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

    List<String> functions;

    // 授权数据
    List<String> departments;

    public List<String> getFunctions() {
        return functions;
    }

    public void setFunctions(List<String> functions) {
        this.functions = functions;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }
}
