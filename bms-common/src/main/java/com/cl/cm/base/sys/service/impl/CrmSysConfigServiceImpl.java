package com.cl.cm.base.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.cm.base.sys.mapper.CrmSysConfigMapper;
import com.cl.cm.base.sys.model.CrmSysConfig;
import com.cl.cm.base.sys.service.CrmSysConfigService;
import com.cl.common.framework.base.BaseMapper;
import com.cl.common.framework.base.BaseServiceImpl;
import com.cl.common.framework.util.BeanUtil;
import com.cl.common.framework.util.ModelUtil;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
import com.cl.common.framework.util.ResponseJson;
import com.cl.common.framework.util.ServiceUtil;
@Service
public class CrmSysConfigServiceImpl extends BaseServiceImpl<CrmSysConfig> implements CrmSysConfigService
{
	private Logger LOGGER = LoggerFactory.getLogger(CrmSysConfigServiceImpl.class);

    @Autowired
    private CrmSysConfigMapper crmSysConfigMapper;
    
    /**
     * 系统配置查询列表
     */
	public PagedResult<CrmSysConfig> selectCrmSysConfigPageList(
			PageBean pageBean, CrmSysConfig crmSysConfig)
	{
		 ServiceUtil.startPage(pageBean);
	     return BeanUtil.toPagedResult(crmSysConfigMapper.selectListByCrmSysConfig(crmSysConfig));
	}

	public BaseMapper getMapper() {
		return this.crmSysConfigMapper;
	}

	public ResponseJson sysConfigEditService(CrmSysConfig crmSysConfig)
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
