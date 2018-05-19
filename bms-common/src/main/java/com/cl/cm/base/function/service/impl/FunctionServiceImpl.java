package com.cl.cm.base.function.service.impl;

import com.cl.cm.base.auth.model.ButtonPower;
import com.cl.cm.base.function.mapper.FunctionMapper;
import com.cl.cm.base.function.model.Function;
import com.cl.cm.base.function.model.FunctionExt;
import com.cl.cm.base.function.model.FunctionTree;
import com.cl.cm.base.function.service.FunctionService;
import com.cl.common.framework.base.BaseMapper;
import com.cl.common.framework.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
    public List<FunctionTree> getFunctionZtreeData(String roleCode) throws Exception {
        return functionMapper.selectFunctionZtreeData(roleCode);
    }

    @Override
    public List<Function> getFuntion(String loginName) {
        return functionMapper.selectByLoginName(loginName, "0");
    }

    @Override
    public ButtonPower getAuthorizationBtn(String loginName, String modelName) throws NoSuchFieldException, IllegalAccessException {
        List<Function> btns = functionMapper.selectBtnByLoginNameAndModelName(loginName, modelName);
        ButtonPower buttonPower = new ButtonPower();
        for (Function fn : btns) {
            setFieldValue(buttonPower, fn.getFunCode());
        }
        return buttonPower;
    }

    void setFieldValue(ButtonPower buttonPower, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class t = buttonPower.getClass();
        Field field = t.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(buttonPower, true);
    }

    @Override
    public List<FunctionExt> getFuntions(String loginName) throws InvocationTargetException, IllegalAccessException {
        List<FunctionExt> functions = this.getFunctionExts(loginName, "123");
        return functions;
    }

    private List<FunctionExt> getFunctionExts(String loginName, String parentID) throws InvocationTargetException, IllegalAccessException {
        List<Function> functions = functionMapper.selectByLoginName(loginName, parentID);
        if (functions == null || functions.size() == 0) {
            return null;
        }
        List<FunctionExt> functionExts = new ArrayList<FunctionExt>(10);
        FunctionExt functionExt;
        for (Function function : functions) {
            functionExt = new FunctionExt();
            functionExt.setId(function.getId());
            functionExt.setFunCode(function.getFunCode());
            functionExt.setFunName(function.getFunName());
            functionExt.setIcon(function.getIcon());
            functionExt.setParentId(function.getParentId());
            functionExt.setUrl(function.getUrl());
            functionExts.add(functionExt);
        }

        List<FunctionExt> functionExts1;
        for (FunctionExt functionext1 : functionExts) {
            functionExts1 = this.getFunctionExts(loginName, functionext1.getId());
            if (functionExts1 == null || functionExts1.size() == 0) {
                continue;
            }
            functionext1.setFunctions(functionExts1);
        }
        return functionExts;
    }
}
