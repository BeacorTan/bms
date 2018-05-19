package com.cl.cm.base.role.service.impl;

import com.cl.cm.base.role.mapper.RoleFunctionMapper;
import com.cl.cm.base.role.model.RoleFunctionMap;
import com.cl.cm.base.role.service.RoleFunctionService;
import com.cl.common.framework.base.BaseMapper;
import com.cl.common.framework.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 部门接口类
 *
 *
 * @date 2017年04月03日
 */
@Service
public class RoleFunctionServiceImpl extends BaseServiceImpl<RoleFunctionMap> implements RoleFunctionService {


    @Resource
    private RoleFunctionMapper roleFunctionMapper;

    @Override
    public BaseMapper getMapper() {
        return roleFunctionMapper;
    }


}
