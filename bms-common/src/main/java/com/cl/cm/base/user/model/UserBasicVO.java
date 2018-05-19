package com.cl.cm.base.user.model;

import java.util.List;

/**
 * 用户
 */
public class UserBasicVO extends UserBasic {

    // 公司名称
    private String companyName;
    // 部门名称
    private String deptName;
    // 组名称
    private String groupName;

    private List<String> roleCodes;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
}
