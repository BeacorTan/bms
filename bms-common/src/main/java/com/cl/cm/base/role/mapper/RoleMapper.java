package com.cl.cm.base.role.mapper;

import com.cl.cm.base.role.model.Role;
import com.cl.cm.base.user.model.UserRoleVO;
import com.cl.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {

  /*  List<Map<String,String>> selectDepZtreeData();*/

    List<Role> selectListByRole(Role role);

    List<UserRoleVO> selectListByLoginName(@Param("loginName") String loginName);
}
