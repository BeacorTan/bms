package com.cl.cm.base.user.model;

import com.cl.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author beson
 */
@Table(name = "T_SYS_USER")
public class UserBasic extends BaseModel implements Serializable {

    // 控台做分机号使用
    @Column(name = "CHANNEL_CODE", length = 30)
    private String channelCode;

    // 登录账号||坐席工号
    @Column(name = "LOGIN_NAME")
    private String loginName;

    // 登录者姓名
    @Column(name = "REAL_NAME")
    private String realName;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    // 公司编码
    @Column(name = "COMPANY_CODE")
    private String companyCode;

    // 部门编码
    @Column(name = "DEPT_CODE")
    private String deptCode;

    // 组编码
    @Column(name = "GROUP_CODE")
    private String groupCode;

    // 职位编码
    @Column(name = "position_code")
    private String positionCode;

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


    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
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

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

}