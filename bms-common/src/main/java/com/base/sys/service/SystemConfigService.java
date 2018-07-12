package com.base.sys.service;

import com.base.sys.model.SystemConfig;
import com.common.framework.base.BaseService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

import java.util.List;

public interface SystemConfigService extends BaseService<SystemConfig> {

    PagedResult<SystemConfig> selectSysConfigPageList(PageBean pageBean, SystemConfig sysConfig);

    ResponseJson editConfig(SystemConfig sysConfig);

    ResponseJson removeByIds(List<String> ids);
}
