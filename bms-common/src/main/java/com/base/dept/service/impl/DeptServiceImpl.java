package com.base.dept.service.impl;

import com.base.dept.mapper.DeptMapper;
import com.base.dept.model.Department;
import com.base.dept.service.DeptService;
import com.base.role.mapper.RoleDataMapper;
import com.base.user.mapper.UserBasicMapper;
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
import com.common.model.TreeVO;
import com.common.shiro.ShiroManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeptServiceImpl extends BaseServiceImpl<Department> implements DeptService {

    @Resource
    private DeptMapper depMapper;

    @Resource
    private UserBasicMapper userBasicMapper;

    @Resource
    private RoleDataMapper roleDataMapper;

    @Override
    public BaseMapper getMapper() {
        return depMapper;
    }

    @Override
    public PagedResult<Department> query(Department department, PageBean pageBean) {
        ServiceUtil.startPage(pageBean);
        if (department != null) {
            if (StringUtils.isNotBlank(department.getDeptCode())
                    || StringUtils.isNotBlank(department.getDeptName())
                    || StringUtils.isNotBlank(department.getDeptType())) {
                department.setParentCode(null);
            } else if (StringUtils.isBlank(department.getParentCode())) {
                department.setParentCode("0");
            }
        }
        return BeanUtil.toPagedResult(depMapper.selectByDepartment(department));
    }

    @Transactional
    @Override
    public ResponseJson edit(Department department) {

        if (department == null) {
            return ServiceUtil.getResponseJson("编辑失败", SystemConstant.RESPONSE_ERROR);
        }
        String parentCode = department.getParentCode();

        if (StringUtils.isNotBlank(parentCode)) {
            Department parentDept = depMapper.selectOne(new Department(parentCode, BaseModel.ACTIVE_FLAG_YES));
            if (parentDept != null) {
                depMapper.updateByDeptCode(new Department(parentCode, "0"));
                department.setParentCodes(parentDept.getParentCodes() + "," + parentCode);
                department.setTreeNames(parentDept.getTreeNames() + "/" + department.getDeptName());
                department.setTreeLevel(parentDept.getTreeLevel() + 1);
                department.setParentName(parentDept.getDeptName());
            }
        }
        if (StringUtils.isNotBlank(department.getId())) {
            // update
            ModelUtil.updateInit(department);
            depMapper.updateByPrimaryKeySelective(department);
        } else {
            // insert
            ModelUtil.insertInit(department);
            // 默认是末级
            department.setTreeLeaf("1");
            depMapper.insertSelective(department);
        }
        return ServiceUtil.getResponseJson(SystemConstant.UPDATE_SUCCESS, SystemConstant.RESPONSE_SUCCESS);
    }

    @Override
    public ResponseJson remove(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ServiceUtil.getResponseJson("删除失败，参数为空", SystemConstant.RESPONSE_ERROR);
        }

        if (userBasicMapper.countUserByDeptIds(ids) > 0) {
            return ServiceUtil.getResponseJson("该部门下已存在员工，请先调整该部门下的员工至其他部门", SystemConstant.RESPONSE_ERROR);
        }

        Department department = new Department();
        ModelUtil.deleteInit(department);
        this.updateActiveFlagByPrimaryKeyList(ids, department);

        Map<String, Object> delMap = new HashMap<String, Object>(2);
        delMap.put("updateBy", ShiroManager.getLoginName());
        delMap.put("updateDate", new Date());
        delMap.put("activeFlag", BaseModel.ACTIVE_FLAG_NO);
        delMap.put("ids", ids);
        roleDataMapper.updateActiveFlagByDeptIds(delMap);
        return ServiceUtil.getResponseJson("删除成功", SystemConstant.RESPONSE_SUCCESS);
    }

    @Override
    public PagedResult<Department> linkage(Department department, PageBean pageBean) {
        if (department == null) {
            department = new Department();
            department.setParentCode("0");
        } else {
            String parentCode = department.getParentCode();
            if (parentCode == null || "".equals(parentCode)) {
                department.setParentCode("0");
            }
        }
        return this.query(department, pageBean);
    }

    @Override
    public List<TreeVO> queryTree(String roleCode) {
        return depMapper.queryTree(roleCode);
    }
}
