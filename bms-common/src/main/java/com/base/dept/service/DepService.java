package com.base.dept.service;

import com.base.dept.model.Department;
import com.base.dept.model.DepartmentVO;
import com.common.framework.base.BaseService;

import java.util.List;

/**
 * 部门接口
 *
 *
 * @date 2017年04月03日
 */
public interface DepService extends BaseService<Department> {
    /**
     * 获取部门树数据
     *
     * @return
     * @throws Exception
     */
    List<DepartmentVO> getDepZtreeData(String roleCode) throws Exception;

}
