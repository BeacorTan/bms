package com.base.role.mapper;

import com.base.role.model.Role;
import com.base.user.model.UserRoleVO;
import com.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {

  /*  List<Map<String,String>> selectDepZtreeData();*/

    List<Role> selectListByRole(Role role);

    List<UserRoleVO> selectListByLoginName(@Param("loginName") String loginName);
}
