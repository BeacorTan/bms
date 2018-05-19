package com.base.role.service.impl;

import com.base.role.model.RoleFunctionMap;
import com.base.role.mapper.RoleFunctionMapper;
import com.base.role.service.RoleFunctionService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
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
