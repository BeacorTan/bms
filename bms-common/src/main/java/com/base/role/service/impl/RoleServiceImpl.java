package com.base.role.service.impl;

import com.base.role.mapper.RoleDataMapper;
import com.base.role.mapper.RoleFunctionMapper;
import com.base.role.mapper.RoleMapper;
import com.base.role.model.Role;
import com.base.role.model.RoleData;
import com.base.role.model.RoleFunctionMap;
import com.base.role.model.RoleVO;
import com.base.role.service.RoleService;
import com.base.user.mapper.UserRoleMapper;
import com.base.user.model.UserRoleVO;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseModel;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import com.common.shiro.ShiroManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门接口类
 *
 * @date 2017年04月03日
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleFunctionMapper roleFunctionMapper;

    @Resource
    private RoleDataMapper roleDataMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public BaseMapper getMapper() {
        return roleMapper;
    }

    @Override
    public PagedResult<Role> selectPageList(PageBean pageBean, Role role) {
        ServiceUtil.startPage(pageBean);
        return BeanUtil.toPagedResult(roleMapper.selectListByRole(role));
    }

    @Override
    public PagedResult<UserRoleVO> selectPageList(PageBean pageBean, String loginName) {
        ServiceUtil.startPage(pageBean);
        return BeanUtil.toPagedResult(roleMapper.selectListByLoginName(loginName));
    }

    @Transactional
    @Override
    public ResponseJson editRole(RoleVO roleVO) {
        if (roleVO == null) {
            return ServiceUtil.getResponseJson("参数为空", SystemConstant.RESPONSE_ERROR);
        }
        String id = roleVO.getId();
        if (StringUtils.isNotBlank(id)) {
            this.updateRole(roleVO);
        } else {
            this.addRole(roleVO);
        }
        return ServiceUtil.getResponseJson(SystemConstant.UPDATE_SUCCESS, SystemConstant.RESPONSE_SUCCESS);
    }

    @Transactional
    @Override
    public ResponseJson removeRoleByKeys(List<String> ids) {

        if (userRoleMapper.isExistByRoleIds(ids) > 0) {
            return ServiceUtil.getResponseJson("请先解除用户与角色的绑定关系", SystemConstant.RESPONSE_ERROR);
        }
        Role role = new Role();
        ModelUtil.deleteInit(role);
        this.updateActiveFlagByPrimaryKeyList(ids, role);

        Map<String,Object> delMap=new HashMap<String,Object>(2);
        delMap.put("updateBy", ShiroManager.getLoginName());
        delMap.put("updateDate",new Date());
        delMap.put("activeFlag",BaseModel.ACTIVE_FLAG_NO);
        delMap.put("ids",ids);

        roleFunctionMapper.updateActiveFlagByRoleIds(delMap);
        roleDataMapper.updateActiveFlagByRoleIds(delMap);
        return ServiceUtil.getResponseJson("删除成功", SystemConstant.RESPONSE_SUCCESS);
    }

    private void updateRole(RoleVO roleVO) {
        ModelUtil.updateInit(roleVO);
        super.updateByPrimaryKeySelective(roleVO);
        RoleFunctionMap roleFunctionMap = new RoleFunctionMap();
        ModelUtil.deleteInit(roleFunctionMap);
        String roleCode = roleVO.getRoleCode();

        Example example = new Example(RoleFunctionMap.class);
        example.createCriteria().andCondition("ROLE_CODE=", roleCode);
        roleFunctionMapper.updateByExampleSelective(roleFunctionMap, example);

        example = new Example(RoleData.class);
        RoleData roleData = new RoleData(roleCode);
        ModelUtil.deleteInit(roleData);
        example.createCriteria().andCondition("ROLE_CODE=", roleCode);
        roleDataMapper.updateByExampleSelective(roleData, example);
        this.editAuth(roleVO, roleCode);
    }

    private void addRole(RoleVO roleVO) {
        String roleCode = "ROLE" + System.currentTimeMillis();
        roleVO.setRoleCode(roleCode);
        ModelUtil.insertInit(roleVO);
        super.insertSelective(roleVO);
        this.editAuth(roleVO, roleCode);
    }

    private void editAuth(RoleVO roleVO, String roleCode) {
        List<String> functionCodes = roleVO.getFunctions();
        if (CollectionUtils.isNotEmpty(functionCodes)) {
            RoleFunctionMap roleFunctionMap;
            for (String functionCode : functionCodes) {
                roleFunctionMap = new RoleFunctionMap(roleCode, functionCode);
                ModelUtil.insertInit(roleFunctionMap);
                roleFunctionMapper.insertSelective(roleFunctionMap);
            }
        }

        List<String> authData = roleVO.getDepartments();
        if (CollectionUtils.isNotEmpty(authData)) {
            RoleData roleData = null;
            for (String deptCode : authData) {
                roleData = new RoleData(roleCode, deptCode);
                ModelUtil.insertInit(roleData);
                roleDataMapper.insertSelective(roleData);
            }
        }
    }
}
