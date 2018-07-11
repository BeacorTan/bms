package com.base.user.model;

import java.util.List;

/**
 * 用户
 */
public class UserBasicVO extends UserBasic {

    private List<String> roleCodes;

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
}
