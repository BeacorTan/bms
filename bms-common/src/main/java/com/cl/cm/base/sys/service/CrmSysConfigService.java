package com.cl.cm.base.sys.service;

import com.cl.cm.base.sys.model.CrmSysConfig;
import com.cl.common.framework.base.BaseService;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
import com.cl.common.framework.util.ResponseJson;

public interface CrmSysConfigService extends BaseService<CrmSysConfig>
{
	PagedResult<CrmSysConfig> selectCrmSysConfigPageList(PageBean pageBean, CrmSysConfig crmSysConfig);
	
	ResponseJson sysConfigEditService(CrmSysConfig crmSysConfig);
}
