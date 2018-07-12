package com.base.user.mapper;

import com.base.user.model.UserRoleMap;
import com.common.framework.base.BaseMapper;

import java.util.List;
import java.util.Map;

public interface UserRoleMapper extends BaseMapper<UserRoleMap> {

    int updateActiveFlagByRecord(UserRoleMap userRoleMap);

//    int updateActiveFlagByUserIds(UserRoleMap userRoleMap,List<String> ids);
    int updateActiveFlagByUserIds(Map map);

    int isExistByRoleIds(List<String> ids);
}
