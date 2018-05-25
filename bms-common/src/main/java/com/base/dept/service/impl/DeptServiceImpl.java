package com.base.dept.service.impl;

import com.base.dept.mapper.DeptMapper;
import com.base.dept.model.Department;
import com.base.dept.service.DeptService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseModel;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptServiceImpl extends BaseServiceImpl<Department> implements DeptService {


    @Resource
    private DeptMapper depMapper;

    @Override
    public BaseMapper getMapper() {
        return depMapper;
    }


    @Override
    public PagedResult<Department> query(Department department, PageBean pageBean) {
        ServiceUtil.startPage(pageBean);
        return BeanUtil.toPagedResult(depMapper.select(department));
    }

    @Transactional
    @Override
    public ResponseJson edit(Department department) {

        if (department == null) {
            return ServiceUtil.getResponseJson("编辑失败", false);
        }
        String parentCode = department.getParentCode();

        if (StringUtils.isNotBlank(parentCode)) {
            Department parentDept = depMapper.selectOne(new Department(parentCode, BaseModel.ACTIVE_FLAG_YES));
            if (parentCode != null) {
                depMapper.updateByDeptCode(new Department(parentCode, "0"));
                department.setParentCodes(parentDept.getParentCodes() + "," + parentCode);
                department.setTreeNames(parentDept.getTreeNames() + "/" + department.getDeptName());
                department.setTreeLevel(parentDept.getTreeLevel() + 1);
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
        return ServiceUtil.getResponseJson("编辑成功", true);
    }

    @Override
    public ResponseJson remove(List<String> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            return ServiceUtil.getResponseJson("删除失败，参数为空", false);
        }
        Department department = new Department();
        ids.forEach((id) -> {
            department.setId(id);
            ModelUtil.deleteInit(department);
            depMapper.updateByPrimaryKeySelective(department);
        });
        return ServiceUtil.getResponseJson("删除成功", true);
    }


}
