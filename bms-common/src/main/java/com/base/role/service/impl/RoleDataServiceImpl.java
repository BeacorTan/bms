package com.base.role.service.impl;

import com.base.role.mapper.RoleDataMapper;
import com.base.role.model.RoleData;
import com.base.role.service.RoleDataService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门接口类
 *
 * @date 2017年04月03日
 */
@Service
public class RoleDataServiceImpl extends BaseServiceImpl<RoleData> implements RoleDataService {

    @Resource
    private RoleDataMapper roleDataMapper;

    @Override
    public BaseMapper getMapper() {
        return roleDataMapper;
    }


    @Override
    public List<RoleData> selectByLoginName(String loginName) {
        return roleDataMapper.selectByLoginName(loginName);
    }
}
