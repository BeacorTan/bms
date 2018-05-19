package com.base.sys.service;

import com.base.sys.model.SystemConfig;
import com.common.framework.base.BaseService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

public interface SystemConfigService extends BaseService<SystemConfig>
{
	PagedResult<SystemConfig> selectCrmSysConfigPageList(PageBean pageBean, SystemConfig crmSysConfig);
	
	ResponseJson sysConfigEditService(SystemConfig crmSysConfig);
}
