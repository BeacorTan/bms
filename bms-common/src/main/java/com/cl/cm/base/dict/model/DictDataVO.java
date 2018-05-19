package com.cl.cm.base.dict.model;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 数据字典
 *
 * @author BoSongsh
 * @create 2018-03-01 17:10
 **/
@Table(name = "T_SYS_DATA_DICTIONARY")
public class DictDataVO extends DictVO {

    @Column(name = "DICT_CODE")
    private String dictCode;


    public DictDataVO() {
    }

    public DictDataVO(String id, Integer activeFlag) {
        this.id = id;
        this.activeFlag = activeFlag;
    }

    public DictDataVO(String dictCode) {
        this.dictCode = dictCode;
    }


    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }
}
