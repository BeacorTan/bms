package com.base.role.service;


import com.base.role.model.Role;
import com.base.role.model.RoleVO;
import com.base.user.model.UserRoleVO;
import com.common.framework.base.BaseService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

import java.util.List;

/**
 * 角色接口
 */
public interface RoleService extends BaseService<Role> {

    PagedResult<Role> selectPageList(PageBean pageBean, Role role);

    PagedResult<UserRoleVO> selectPageList(PageBean pageBean, String loginName);

    ResponseJson editRole(RoleVO roleVO);

    ResponseJson removeRoleByKeys(List<String> ids);
}
