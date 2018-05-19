package com.base.function.model;

import com.common.framework.base.BaseModel;


/**
 * 菜单
 */
public class FunctionVO extends BaseModel {

    // 默认菜单未被选中
    private Boolean checked = false;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
