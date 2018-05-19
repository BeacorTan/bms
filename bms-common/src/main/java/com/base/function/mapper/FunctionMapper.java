package com.base.function.mapper;

import com.base.function.model.Function;
import com.base.function.model.FunctionTree;
import com.common.framework.base.BaseMapper;
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
