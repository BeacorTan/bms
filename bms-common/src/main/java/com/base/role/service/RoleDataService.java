package com.base.role.service;


import com.base.role.model.RoleData;
import com.common.framework.base.BaseService;

import java.util.List;

/**
 * 角色数据接口
 */
public interface RoleDataService extends BaseService<RoleData> {

    List<RoleData> selectByLoginName(String loginName);

}
