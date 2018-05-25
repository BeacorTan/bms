package com.base.dept.service.impl;

import com.alibaba.druid.sql.PagerUtils;
import com.base.dept.mapper.DeptMapper;
import com.base.dept.model.Department;
import com.base.dept.model.DepartmentVO;
import com.base.dept.service.DeptService;
import com.common.framework.base.BaseMapper;
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

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门接口类
 *
 * @date 2017年04月03日
 */
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

    @Override
    public ResponseJson edit(Department department) {

        if (department == null) {
            return ServiceUtil.getResponseJson("编辑失败", false);
        }
        if (StringUtils.isNotBlank(department.getId())) {
            // update
            ModelUtil.updateInit(department);
            depMapper.updateByPrimaryKeySelective(department);
        } else {
            // insert
            ModelUtil.insertInit(department);
//            String parentCodes;
//            department.setParentCodes(parentCodes);
            department.setTreeLeaf("0");
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
