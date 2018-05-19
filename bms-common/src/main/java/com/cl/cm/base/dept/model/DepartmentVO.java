package com.cl.cm.base.dept.model;


public class DepartmentVO extends Department {

    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}