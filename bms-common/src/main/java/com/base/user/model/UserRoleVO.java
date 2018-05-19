package com.base.user.model;

import com.base.role.model.Role;

public class UserRoleVO extends Role {

    private boolean state = false;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
