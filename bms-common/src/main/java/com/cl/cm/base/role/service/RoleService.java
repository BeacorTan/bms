package com.cl.cm.base.role.service;


import com.cl.cm.base.role.model.Role;
import com.cl.cm.base.role.model.RoleVO;
import com.cl.cm.base.user.model.UserRoleVO;
import com.cl.common.framework.base.BaseService;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;

/**
 * 角色接口
 */
public interface RoleService extends BaseService<Role> {

    PagedResult<Role> selectPageList(PageBean pageBean, Role role);

    PagedResult<UserRoleVO> selectPageList(PageBean pageBean, String loginName);

    void addRoleAndRoleFunction(RoleVO roleVO) throws Exception;

    void updateRoleAndRoleFunction(RoleVO roleVO) throws Exception;
}
