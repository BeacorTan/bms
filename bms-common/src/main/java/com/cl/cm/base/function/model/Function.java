package com.cl.cm.base.function.model;

import com.cl.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 资源对象-菜单和按钮
 *
 *
 * @date 2017年04月07日
 */
@Table(name = "t_sys_function")
public class Function extends BaseModel {


    private String description;


    /**
     * 菜单编码,手动输入、唯一、有规则
     */
    @Column(length = 32, nullable = false, name = "FUN_CODE")
    private String funCode;
    /**
     * 菜单名称
     */
    @Column(length = 60, nullable = false, name = "FUN_NAME")
    private String funName;
    /**
     * 排序号
     */
    @Column(length = 30, name = "ORDER_NUMBER")
    private Integer orderNumber;
    /**
     * 地址
     */
    @Column(length = 60, name = "URL")
    private String url;
    /**
     * 父菜单
     */
    @Column(length = 32, name = "PARENT_ID")
    private String parentId;
    /**
     * 菜单图标
     */
    @Column(length = 32, name = "ICON")
    private String icon;

    @Column(name = "TYPE")
    private Integer type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
