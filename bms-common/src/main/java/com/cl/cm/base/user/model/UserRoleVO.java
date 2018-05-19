package com.cl.cm.base.user.model;

import com.cl.cm.base.role.model.Role;

public class UserRoleVO extends Role {

    private boolean state = false;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
