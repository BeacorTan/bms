package com.cl.cm.base.sys.mapper;
import java.util.List;

import com.cl.cm.base.sys.model.CrmSysConfig;
import com.cl.common.framework.base.BaseMapper;

/**
 * CrmSysConfigDAO
 * @author XingyuLi
 *
 */
public interface CrmSysConfigMapper extends BaseMapper<CrmSysConfig>
{
	 List<CrmSysConfig> selectListByCrmSysConfig(CrmSysConfig crmSysConfig);
}
