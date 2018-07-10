package com.base.function.service;

import com.base.function.model.Function;
import com.base.function.model.FunctionExt;
import com.common.framework.base.BaseService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.model.TreeVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 功能资源接口
 *
 * @date 2017年04月07日
 */

public interface FunctionService extends BaseService<Function> {

    PagedResult<Function> queryByFunction(Function function, PageBean pageBean);

    List<FunctionExt> getFunctions(String loginName) throws InvocationTargetException, IllegalAccessException;

    List<TreeVO> queryTree(String roleCode);

    ResponseJson functionEdit(Function function);

    List<String> getPermissions(String loginName);

    ResponseJson updateActiveFlagByPrimaryKeyList(List<String> keys);
}
