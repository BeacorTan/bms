package com.base.sys.mapper;
import java.util.List;

import com.base.sys.model.SystemConfig;
import com.common.framework.base.BaseMapper;

/**
 *
 *
 */
public interface SystemConfigMapper extends BaseMapper<SystemConfig>
{
	 List<SystemConfig> selectListBySysConfig(SystemConfig sysConfig);
}
