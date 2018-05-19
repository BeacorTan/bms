package com.cl.cm.base.dept.mapper;

import com.cl.cm.base.dept.model.Department;
import com.cl.cm.base.dept.model.DepartmentVO;
import com.cl.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DepMapper extends BaseMapper<Department> {

    List<DepartmentVO> selectDepZtreeData(@Param("roleCode") String roleCode);
}
