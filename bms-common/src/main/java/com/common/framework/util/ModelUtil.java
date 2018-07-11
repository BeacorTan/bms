package com.common.framework.util;

import com.common.framework.base.BaseModel;
import com.common.shiro.ShiroManager;

import java.util.Date;

/**
 * @date 2017年03月07日
 */
public class ModelUtil {
    /**
     * 增加操作的时候,调用当前方法,对共用字段(createBy和createDate)进行初始化
     *
     * @param baseModel
     */
    public static void insertInit(BaseModel baseModel) {
        Date systemDate = new Date();
        String systemUser = ShiroManager.getLoginName();
        baseModel.setCreateBy(systemUser);
        baseModel.setCreateDate(systemDate);
        baseModel.setUpdateBy(systemUser);
        baseModel.setUpdateDate(systemDate);
        baseModel.setActiveFlag(BaseModel.ACTIVE_FLAG_YES);
        baseModel.setId(HelpUtils.getUUID());
    }


    /**
     * 修改操作的时候,调用当前方法,对共用字段(updateBy和updateDate)进行修改
     *
     * @param baseModel
     */
    public static void updateInit(BaseModel baseModel) {
        baseModel.setUpdateBy(ShiroManager.getLoginName());
        baseModel.setUpdateDate(new Date());
    }

    public static void deleteInit(BaseModel baseModel) {
        baseModel.setActiveFlag(BaseModel.ACTIVE_FLAG_NO);
        updateInit(baseModel);
    }

}
