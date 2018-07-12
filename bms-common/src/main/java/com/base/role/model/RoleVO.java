package com.base.role.model;

import java.util.List;

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
