package com.base.dept.service.impl;

import com.base.dept.mapper.DepMapper;
import com.base.dept.model.Department;
import com.base.dept.model.DepartmentVO;
import com.base.dept.service.DepService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门接口类
 *
 *
 * @date 2017年04月03日
 */
@Service
public class DepServiceImpl extends BaseServiceImpl<Department> implements DepService {
    @Resource
    private DepMapper depMapper;

    @Override
    public BaseMapper getMapper() {
        return depMapper;
    }

    @Override
    public List<DepartmentVO> getDepZtreeData(String roleCode) throws Exception {
        return depMapper.selectDepZtreeData(roleCode);
    }
}
