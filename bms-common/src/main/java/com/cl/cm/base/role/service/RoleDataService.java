package com.cl.cm.base.role.service;


import com.cl.cm.base.role.model.RoleData;
import com.cl.common.framework.base.BaseService;

import java.util.List;

/**
 * 角色数据接口
 */
public interface RoleDataService extends BaseService<RoleData> {

    List<RoleData> selectByLoginName(String loginName);

}
