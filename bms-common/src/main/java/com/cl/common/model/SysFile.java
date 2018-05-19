package com.cl.common.model;

import com.cl.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 系统文件
 *
 */
@Entity(name = "T_SYS_FILE")
public class SysFile extends BaseModel{
    /**文件相关联的对象的主键 */
    @Column(name="PARENT_ID")
    private String parentId;

    /**文件名称 */
    @Column(name="NAME")
    private String name;

    /**文件类型*/
    @Column(name = "TYPE")
    private String type;

    /**文件大小 */
    @Column(name="SIZE")
    private Long size;

    /**文件扩展名 */
    @Column(name="EXT")
    private String ext;

    /**文件在服务器上的地址 */
    @Column(name="FILE_PATH")
    private String filePath;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentId() {return parentId;}

    public void setParentId(String parentId) {this.parentId = parentId == null ? null : parentId.trim();}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public Long  getSize() {
        return size;
    }

    public void setSize(Long  size) { this.size = size; }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }



}
