package com.cl.common.framework.util;

import com.cl.common.shiro.ShiroManager;
import com.cl.common.framework.base.BaseModel;

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
        if (HelpUtils.isNotEmpty(baseModel)) {
            Date systemDate = new Date();
            String systemUser = ShiroManager.getLoginName();
            baseModel.setCreateBy(systemUser);
            baseModel.setCreateDate(systemDate);
            baseModel.setUpdateBy(systemUser);
            baseModel.setUpdateDate(systemDate);
            baseModel.setActiveFlag(BaseModel.ACTIVE_FLAG_YES);

            /* 主键处理 */
            if (HelpUtils.isEmpty(baseModel.getId())) {
                baseModel.setId(HelpUtils.getUUID());
            }
        }
    }


    /**
     * 修改操作的时候,调用当前方法,对共用字段(updateBy和updateDate)进行修改
     *
     * @param baseModel
     */
    public static void updateInit(BaseModel baseModel) {
        if (HelpUtils.isNotEmpty(baseModel)) {
            baseModel.setUpdateBy(ShiroManager.getLoginName());
            baseModel.setUpdateDate(new Date());
        }
    }

    public static void deleteInit(BaseModel baseModel) {
        if (HelpUtils.isNotEmpty(baseModel)) {
            baseModel.setActiveFlag(BaseModel.ACTIVE_FLAG_NO);
            updateInit(baseModel);
        }
    }


}
