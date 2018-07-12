package com.base.dept.mapper;

import com.base.dept.model.Department;
import com.common.framework.base.BaseMapper;
import com.common.model.TreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DeptMapper extends BaseMapper<Department> {

    int updateByDeptCode(Department department);

    List<Department> selectByDepartment(Department department);

    List<TreeVO> queryTree(@Param("roleCode") String roleCode);
}
