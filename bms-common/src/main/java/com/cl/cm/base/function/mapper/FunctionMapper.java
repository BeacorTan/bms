package com.cl.cm.base.function.mapper;

import com.cl.cm.base.function.model.Function;
import com.cl.cm.base.function.model.FunctionTree;
import com.cl.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能资源
 */

public interface FunctionMapper extends BaseMapper<Function> {

    List<FunctionTree> selectFunctionZtreeData(@Param("roleCode") String roleCode);

    List<Function> selectByParentID(@Param("parentID") String parentID);


    List<Function> selectBtnByLoginNameAndModelName(@Param("loginName") String loginName,@Param("modelName") String modelName);

    List<Function> selectByLoginName(@Param("loginName") String loginName, @Param("parentID") String parentID);
}
