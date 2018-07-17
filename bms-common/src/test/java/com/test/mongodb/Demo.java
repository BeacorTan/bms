package com.test.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * 标的出款额度请求实体类
 * Created by HaichangZhang on 2017/4/14.
 */
@Document(collection = "user")
public class Demo {

    /***
     *随机GUID
     */
    private String uuid;

  
    private String  name;

    private List<String>  info;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}
