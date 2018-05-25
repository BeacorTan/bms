package com.base.dept.service;

import com.base.dept.model.Department;
import com.common.framework.base.BaseService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

import java.util.List;

/**
 * 部门接口
 *
 * @date 2017年04月03日
 */
public interface DeptService extends BaseService<Department> {

    /**
     * 获取部门树数据
     *
     * @return
     * @throws Exception
     */
    PagedResult<Department> query(Department department, PageBean pageBean);

    ResponseJson edit(Department department);

    ResponseJson remove(List<String> ids);


}
