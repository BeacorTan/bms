package com.cl.cm.base.auth.model;


/**
 * 按钮权限
 *
 * @author BoSongsh
 * @create 2018-04-04 16:21
 **/
public class ButtonPower {

    private boolean query;

    private boolean add;

    private boolean delete;

    private boolean update;

    // 下载
    private boolean download;
    // 上传
    private boolean uploadFile;

    public boolean isQuery() {
        return query;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public boolean isUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(boolean uploadFile) {
        this.uploadFile = uploadFile;
    }
}
