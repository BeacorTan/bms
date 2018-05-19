package com.base.role.mapper;

import com.base.role.model.RoleData;
import com.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleDataMapper extends BaseMapper<RoleData> {

    List<RoleData> selectByLoginName(@Param("loginName") String loginName);
}
