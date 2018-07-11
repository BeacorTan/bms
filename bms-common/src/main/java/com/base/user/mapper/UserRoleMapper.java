package com.base.user.mapper;

import com.base.user.model.UserRoleMap;
import com.common.framework.base.BaseMapper;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRoleMap> {

    int updateActiveFlagByRecord(UserRoleMap userRoleMap);

    int updateActiveFlagByUserIds(UserRoleMap userRoleMap,List<String> ids);
}
