package com.base.sys.service.impl;

import com.base.sys.mapper.SystemConfigMapper;
import com.base.sys.model.SystemConfig;
import com.base.sys.service.SystemConfigService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig> implements SystemConfigService {

    @Autowired
    private SystemConfigMapper sysConfigMapper;

    /**
     * 系统配置查询列表
     */
    @Override
    public PagedResult<SystemConfig> selectSysConfigPageList(PageBean pageBean, SystemConfig sysConfig) {
        ServiceUtil.startPage(pageBean);
        return BeanUtil.toPagedResult(sysConfigMapper.selectListBySysConfig(sysConfig));
    }


    @Transactional
    @Override
    public ResponseJson editConfig(SystemConfig sysConfig) {
        if (sysConfig == null) {
            return ServiceUtil.getResponseJson("编辑失败", SystemConstant.RESPONSE_ERROR);
        }

        String id=sysConfig.getId();
        if (StringUtils.isEmpty(id)) {
            ModelUtil.insertInit(sysConfig);
            sysConfigMapper.insertSelective(sysConfig);
        } else {
            ModelUtil.updateInit(sysConfig);
            sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
        }
        return ServiceUtil.getResponseJson("编辑失败", SystemConstant.RESPONSE_SUCCESS);
    }

    @Override
    public ResponseJson removeByIds(List<String> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return ServiceUtil.getResponseJson("删除失败，参数为空", SystemConstant.RESPONSE_ERROR);
        }

        SystemConfig sysConfig = new SystemConfig();
        ModelUtil.deleteInit(sysConfig);
        this.updateActiveFlagByPrimaryKeyList(ids,sysConfig);
        return ServiceUtil.getResponseJson("删除成功", SystemConstant.RESPONSE_SUCCESS);
    }

    public BaseMapper getMapper() {
        return this.sysConfigMapper;
    }
}
