package com.base.sys.service.impl;

import com.base.sys.mapper.SystemConfigMapper;
import com.base.sys.model.SystemConfig;
import com.base.sys.service.SystemConfigService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig> implements SystemConfigService {
//    private Logger LOGGER = LoggerFactory.getLogger(SystemConfigServiceImpl.class);

    @Autowired
    private SystemConfigMapper sysConfigMapper;

    /**
     * 系统配置查询列表
     */
    public PagedResult<SystemConfig> selectSysConfigPageList(PageBean pageBean, SystemConfig sysConfig) {
        ServiceUtil.startPage(pageBean);
        return BeanUtil.toPagedResult(sysConfigMapper.selectListBySysConfig(sysConfig));
    }



    public ResponseJson updateSysConfig(SystemConfig sysConfig) {
        if (sysConfig == null) {
            return ServiceUtil.getResponseJson("编辑失败", false);
        }

        boolean flag = true;
        String msg = "编辑成功";
        String id = sysConfig.getId();

        try {
            if (StringUtils.isEmpty(id)) {
                ModelUtil.insertInit(sysConfig);
                sysConfigMapper.insertSelective(sysConfig);
            } else {
                ModelUtil.updateInit(sysConfig);
                sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
            }
        } catch (Exception e) {
            flag = false;
            msg = e.getMessage();
        }
        return ServiceUtil.getResponseJson(msg, flag);
    }
    public BaseMapper getMapper() {
        return this.sysConfigMapper;
    }
}
