package com.base.user.model;


public class UpdateUserPwdVO extends UserBasic {

    // 确认新密码
    private String confirmPwd;

    // 新密码
    private String newPwd;

    // 当前密码
    private String currentPwd;

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

    public String getCurrentPwd() {
        return currentPwd;
    }

    public void setCurrentPwd(String currentPwd) {
        this.currentPwd = currentPwd;
    }
}
