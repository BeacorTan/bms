package com.base.sys.service.impl;

import com.base.sys.mapper.SystemConfigMapper;
import com.base.sys.service.SystemConfigService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.sys.model.SystemConfig;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
@Service
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig> implements SystemConfigService
{
	private Logger LOGGER = LoggerFactory.getLogger(SystemConfigServiceImpl.class);

    @Autowired
    private SystemConfigMapper crmSysConfigMapper;
    
    /**
     * 系统配置查询列表
     */
	public PagedResult<SystemConfig> selectCrmSysConfigPageList(
			PageBean pageBean, SystemConfig crmSysConfig)
	{
		 ServiceUtil.startPage(pageBean);
	     return BeanUtil.toPagedResult(crmSysConfigMapper.selectListByCrmSysConfig(crmSysConfig));
	}

	public BaseMapper getMapper() {
		return this.crmSysConfigMapper;
	}

	public ResponseJson sysConfigEditService(SystemConfig crmSysConfig)
	{
		if (crmSysConfig == null) {
            return ServiceUtil.getResponseJson("编辑失败", false);
        }

        boolean flag = true;
        String msg = "编辑成功";
        String id = crmSysConfig.getId();

        try {
            if (StringUtils.isEmpty(id)) {
                ModelUtil.insertInit(crmSysConfig);
                crmSysConfigMapper.insertSelective(crmSysConfig);
            } else {
                ModelUtil.updateInit(crmSysConfig);
                crmSysConfigMapper.updateByPrimaryKeySelective(crmSysConfig);
            }
        } catch (Exception e) {
            flag = false;
            msg = e.getMessage();
        }
        return ServiceUtil.getResponseJson(msg, flag);
	}
}
