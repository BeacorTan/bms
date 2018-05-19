package com.cl.cm.base.dept.service.impl;

import com.cl.cm.base.dept.model.Department;
import com.cl.cm.base.dept.model.DepartmentVO;
import com.cl.cm.base.dept.service.DepService;
import com.cl.cm.base.dept.mapper.DepMapper;
import com.cl.common.framework.base.BaseMapper;
import com.cl.common.framework.base.BaseServiceImpl;
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
