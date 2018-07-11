package com.base.user.model;

import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author beson
 */
@Table(name = "T_SYS_USER")
public class UserBasic extends BaseModel implements Serializable {

    // 登录账号
    @Column(name = "LOGIN_NAME")
    private String loginName;

    // 登录者姓名
    @Column(name = "NAME")
    private String name;

    @Column(name = "SEX")
    private Integer sex;

    @Column(name = "phone")
    private String phone;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    // 部门编码
    @Column(name = "DEPT_CODE")
    private String deptCode;

    // 部门编码
    @Column(name = "DEPT_NAME")
    private String deptName;

    // 职位编码
    @Column(name = "POSITION_CODE")
    private String positionCode;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "LAST_LOGIN_IP")
    private String lastLoginIP;

    @Column(name = "LAST_LOGIN_DATE")
    private Date lastLoginDate;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "SIGN")
    private String sign;

    @Column(name = "REMARKS")
    private String remarks;

    /**
     * 密码加密盐值
     */
    @Column(name = "SALT", length = 64)
    private String salt;//加密密码的盐


    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }


    /**
     * 密码盐.
     *
     * @return
     */
    public String getCredentialsSalt() {
        return this.loginName + this.salt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}