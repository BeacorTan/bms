package com.base.function.service.impl;

import com.base.function.mapper.FunctionMapper;
import com.base.function.model.Function;
import com.base.function.model.FunctionExt;
import com.base.function.service.FunctionService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.*;
import com.common.model.TreeVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;

/**
 * 功能资源接口实现类
 */
@Service
public class FunctionServiceImpl extends BaseServiceImpl<Function> implements FunctionService {


    @Transactional
    @Override
    public ResponseJson updateActiveFlagByPrimaryKeyList(List<String> keys) {

        if (CollectionUtils.isEmpty(keys)) {
            return ServiceUtil.getResponseJson("删除失败，上传数据为空", SystemConstant.RESPONSE_ERROR);
        }

        Function function = new Function();
        ModelUtil.deleteInit(function);
        String funName;
        StringBuilder msg = new StringBuilder();
        for (String id : keys) {
            funName = functionMapper.isExistsPermissionsById(id);
            if (StringUtils.isNotBlank(funName)) {
                msg.append(funName);
                msg.append(",");
                continue;
            }
            function.setId(id);
            functionMapper.updateByPrimaryKeySelective(function);
        }
        if (StringUtils.isNotBlank(msg.toString())) {
            String m = MessageFormat.format("不能删除以下菜单:{0},请先删除权限", msg.substring(0, msg.length() - 1));
            return ServiceUtil.getResponseJson(m, SystemConstant.RESPONSE_ERROR);
        }
        return ServiceUtil.getResponseJson(SystemConstant.UPDATE_SUCCESS, SystemConstant.RESPONSE_SUCCESS);

    }

    @Transactional
    @Override
    public ResponseJson functionEdit(Function function) {

        if (function == null) {
            return ServiceUtil.getResponseJson("输入数据为空", SystemConstant.RESPONSE_ERROR);
        }
        String parentCode = function.getParentCode();
        String id = function.getId();
        Function parentFunction = functionMapper.selectByCode(parentCode);
        function.setParentName(parentFunction.getFunName());
        function.setParentCodes(parentFunction.getParentCodes() + "/" + parentCode);
        function.setTreeLevel(parentFunction.getTreeLevel() + 1);

        Integer parentLeaf = parentFunction.getTreeLeaf();

        if (StringUtils.isNotBlank(id)) {
            ModelUtil.updateInit(function);
            functionMapper.updateByPrimaryKeySelective(function);
        } else {
            ModelUtil.insertInit(function);
            // 默认末级
            function.setTreeLeaf(1);
            functionMapper.insertSelective(function);
        }
        // 如果父菜单是末级则更新为非末级（0）
        if (parentLeaf == 1) {
            functionMapper.updateLeafByCode(parentCode, 0);
        }
        return ServiceUtil.getResponseJson(SystemConstant.UPDATE_SUCCESS, SystemConstant.RESPONSE_SUCCESS);
    }

    @Override
    public List<String> getPermissions(String loginName) {
        return functionMapper.selectPermissionByLoginName(loginName);
    }

    /**
     * 菜单首页查询
     *
     * @param function
     * @param pageBean
     * @return
     */
    @Override
    public PagedResult<Function> queryByFunction(Function function, PageBean pageBean) {
        ServiceUtil.startPage(pageBean);
        if (function != null) {
            if (StringUtils.isBlank(function.getFunCode()) && StringUtils.isBlank(function.getFunName()) && StringUtils.isBlank(function.getParentCode())) {
                function.setParentCode("1");
            }
        }
        return BeanUtil.toPagedResult(functionMapper.selectByFunction(function));
    }


    /**
     * 菜单树
     *
     * @param roleCode
     * @return
     */
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
     *
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

    @Resource
    private FunctionMapper functionMapper;

    @Override
    public BaseMapper getMapper() {
        return functionMapper;
    }

}