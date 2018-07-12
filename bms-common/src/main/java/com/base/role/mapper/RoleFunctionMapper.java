package com.base.role.mapper;

import com.base.role.model.RoleFunctionMap;
import com.common.framework.base.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 角色关系
 */
public interface RoleFunctionMapper extends BaseMapper<RoleFunctionMap> {

//
//    List<Role> selectListByRole(Role role);

    /**
     *
     * @param map
     * @return
     */
    int updateActiveFlagByRoleIds(Map map);
}
