package com.base.dept.mapper;

import com.base.dept.model.DepartmentVO;
import com.base.dept.model.Department;
import com.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DepMapper extends BaseMapper<Department> {

    List<DepartmentVO> selectDepZtreeData(@Param("roleCode") String roleCode);
}
