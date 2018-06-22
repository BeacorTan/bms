package com.base.dept.mapper;

import com.base.dept.model.DepartmentVO;
import com.base.dept.model.Department;
import com.common.framework.base.BaseMapper;
import com.common.model.TreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DeptMapper extends BaseMapper<Department> {

    List<DepartmentVO> selectDepZtreeData(@Param("roleCode") String roleCode);

    int updateByDeptCode(Department department);

    List<Department> selectByDepartment(Department department);

    List<TreeVO> queryTree();
}
