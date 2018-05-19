package com.cl.cm.base.function.service;

import com.cl.cm.base.auth.model.ButtonPower;
import com.cl.cm.base.function.model.Function;
import com.cl.cm.base.function.model.FunctionExt;
import com.cl.cm.base.function.model.FunctionTree;
import com.cl.common.framework.base.BaseService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 功能资源接口
 *
 * @date 2017年04月07日
 */

public interface FunctionService extends BaseService<Function> {
    /**
     * 获取资源树数据
     *
     * @return
     * @throws Exception
     */
    List<FunctionTree> getFunctionZtreeData(String roleCode) throws Exception;

    List<Function> getFuntion(String loginName);

    /**
     * 获取授权的按钮
     *
     * @param loginName
     * @return
     */
    ButtonPower getAuthorizationBtn(String loginName, String modelName) throws NoSuchFieldException, IllegalAccessException;

    List<FunctionExt> getFuntions(String loginName) throws InvocationTargetException, IllegalAccessException;
}
