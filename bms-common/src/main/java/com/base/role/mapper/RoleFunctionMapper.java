package com.base.role.mapper;

import com.base.role.model.RoleFunctionMap;
import com.common.framework.base.BaseMapper;

import java.util.Map;

/**
 * 角色关系
 */
public interface RoleFunctionMapper extends BaseMapper<RoleFunctionMap> {


    /**
     *
     * @param map
     * @return
     */
    int updateActiveFlagByRoleIds(Map map);
}
