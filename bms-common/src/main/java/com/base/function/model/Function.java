package com.base.function.model;

import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 资源对象-菜单和按钮
 *
 *
 * @date 2017年04月07日
 */
@Table(name = "T_SYS_FUNCTION")
public class Function extends BaseModel {

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
     * 父菜单
     */
    @Column(length = 32, name = "PARENT_CODE")
    private String parentCode;

    /**
     * 父菜单
     */
    @Column(length = 32, name = "PARENT_NAME")
    private String parentName;

    /**
     * 所有父菜单
     */
    @Column(length = 32, name = "PARENT_CODES")
    private String parentCodes;

    /**
     * 排序号
     */
    @Column(length = 30, name = "TREE_SORT")
    private Integer treeSort;
    /**
     * 地址
     */
    @Column(length = 60, name = "FUN_HREF")
    private String url;

    /**
     * 菜单图标
     */
    @Column(length = 32, name = "FUN_ICON")
    private String icon;

    /**
     * 菜单字体颜色
     */
    @Column(length = 32, name = "FUN_COLOR")
    private String funColor;

    // 是否最末级
    @Column(length = 32, name = "TREE_LEAF")
    private Integer treeLeaf;

    // 层次级别
    @Column(length = 32, name = "TREE_LEVEL")
    private Integer treeLevel;

    // 菜单类型 1：菜单  0：权限按钮
    @Column(name = "TYPE")
    private Integer type;

    private String description;

    public Integer getTreeSort() {
        return treeSort;
    }

    public void setTreeSort(Integer treeSort) {
        this.treeSort = treeSort;
    }

    public Integer getTreeLeaf() {
        return treeLeaf;
    }

    public void setTreeLeaf(Integer treeLeaf) {
        this.treeLeaf = treeLeaf;
    }

    public Integer getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getParentCodes() {
        return parentCodes;
    }

    public void setParentCodes(String parentCodes) {
        this.parentCodes = parentCodes;
    }

    public String getFunColor() {
        return funColor;
    }

    public void setFunColor(String funColor) {
        this.funColor = funColor;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
