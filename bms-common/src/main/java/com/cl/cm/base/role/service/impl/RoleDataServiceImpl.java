package com.cl.cm.base.role.service.impl;

import com.cl.cm.base.role.mapper.RoleDataMapper;
import com.cl.cm.base.role.model.RoleData;
import com.cl.cm.base.role.service.RoleDataService;
import com.cl.common.framework.base.BaseMapper;
import com.cl.common.framework.base.BaseServiceImpl;
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
