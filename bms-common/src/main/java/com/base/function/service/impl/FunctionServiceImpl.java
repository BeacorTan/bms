package com.base.function.service.impl;

import com.base.function.mapper.FunctionMapper;
import com.base.function.model.Function;
import com.base.function.model.FunctionExt;
import com.base.function.service.FunctionService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ServiceUtil;
import com.common.model.TreeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 功能资源接口实现类
 */
@Service
public class FunctionServiceImpl extends BaseServiceImpl<Function> implements FunctionService {
    @Resource
    private FunctionMapper functionMapper;

    @Override
    public BaseMapper getMapper() {
        return functionMapper;
    }


    @Override
    public PagedResult<Function> queryByFunction(Function function, PageBean pageBean) {
        ServiceUtil.startPage(pageBean);
        if (function != null) {
            if (StringUtils.isBlank(function.getParentCode())) {
                function.setParentCode("1");
            }
        }
        return BeanUtil.toPagedResult(functionMapper.selectByFunction(function));
    }


    @Override
    public List<TreeVO> queryTree(String roleCode) {
        return functionMapper.queryFunctionTree(roleCode);
    }

    @Override
    public List<FunctionExt> getFunctions(String loginName) throws InvocationTargetException, IllegalAccessException {
        List<FunctionExt> functions = this.getFunctionExts(loginName, "1");
        return functions;
    }

    /**
     * 递归查询
     * @param loginName
     * @param parentCode
     * @return
     */
    private List<FunctionExt> getFunctionExts(String loginName, String parentCode) {

        List<FunctionExt> functions = functionMapper.selectByLoginName(loginName, parentCode);
        if (functions == null || functions.size() == 0) {
            return null;
        }
        List<FunctionExt> functionExts;
        for (FunctionExt functionExt : functions) {
            // 末级节点
            if (functionExt.getTreeLeaf() == 1) {
                continue;
            }
            functionExts = this.getFunctionExts(loginName, functionExt.getFunCode());
            if (functionExts == null || functionExts.size() == 0) {
                continue;
            }
            functionExt.setFunctions(functionExts);
        }
        return functions;
    }
}
