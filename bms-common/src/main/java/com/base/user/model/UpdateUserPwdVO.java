package com.base.user.model;

/**
 * 用户
 */
public class UpdateUserPwdVO extends UserBasic {

    // 用户当前密码
    private String currentPwd;
    // 新密码
    private String confirmPwd;
    // 确认新密码
    private String newPwd;

    public String getCurrentPwd() {
        return currentPwd;
    }

    public void setCurrentPwd(String currentPwd) {
        this.currentPwd = currentPwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
