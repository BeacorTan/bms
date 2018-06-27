package com.base.function.mapper;

import com.base.function.model.Function;
import com.base.function.model.FunctionExt;
import com.common.framework.base.BaseMapper;
import com.common.model.TreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能资源
 */

public interface FunctionMapper extends BaseMapper<Function> {

    List<FunctionExt> selectByLoginName(@Param("loginName") String loginName, @Param("parentCode") String parentCode);

    List<TreeVO> queryFunctionTree(@Param("roleCode") String roleCode);
}
