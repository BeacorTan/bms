package com.cl.cm.base.role.mapper;

import com.cl.cm.base.role.model.RoleData;
import com.cl.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleDataMapper extends BaseMapper<RoleData> {

    List<RoleData> selectByLoginName(@Param("loginName") String loginName);
}
