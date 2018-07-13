package com.base.quarz.service.impl;

import com.base.quarz.service.SchedulerService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.HelpUtils;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import com.base.quarz.mapper.SchedulerMapper;
import com.base.quarz.model.SystemScheduler;
import com.common.quartz.SchedulerManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 调度接口
 *
 * @date 2017年04月11日
 */
@Service
public class SchedulerServiceImpl extends BaseServiceImpl<SystemScheduler> implements SchedulerService {

    @Resource
    private SchedulerMapper schedulerMapper;

    @Resource
    private SchedulerManager schedulerManager;

    @Override
    public BaseMapper getMapper() {
        return schedulerMapper;
    }

    @Override
    public ResponseJson changeStatus(List<SystemScheduler> systemSchedulers, Integer isStart) {
        if (HelpUtils.isNotEmpty(systemSchedulers)) {
            for (SystemScheduler scheduler : systemSchedulers) {
                scheduler.setStart(isStart);
                schedulerMapper.updateByPrimaryKeySelective(scheduler);
                schedulerManager.updateScheduler(scheduler);
            }
        } else {
            return ServiceUtil.getResponseJson("操作失败！传入参数为空", SystemConstant.RESPONSE_ERROR);
        }
        return ServiceUtil.getResponseJson("操作成功", SystemConstant.RESPONSE_SUCCESS);
    }

    @Transactional
    @Override
    public ResponseJson editScheduler(SystemScheduler scheduler) {
        if (scheduler == null) {
            return ServiceUtil.getResponseJson("编辑失败", SystemConstant.RESPONSE_ERROR);
        }

        String id = scheduler.getId();
        if (StringUtils.isEmpty(id)) {
            ModelUtil.insertInit(scheduler);
            // 默认启动
            scheduler.setStart(1);
            schedulerMapper.insertSelective(scheduler);
            schedulerManager.addScheduler(scheduler);
        } else {
            ModelUtil.updateInit(scheduler);
            schedulerMapper.updateByPrimaryKeySelective(scheduler);
            schedulerManager.updateScheduler(scheduler);
        }
        return ServiceUtil.getResponseJson(SystemConstant.UPDATE_SUCCESS, SystemConstant.RESPONSE_SUCCESS);
    }

    @Override
    public PagedResult<SystemScheduler> findPageListByName(PageBean pageBean, SystemScheduler systemScheduler) {
        ServiceUtil.startPage(pageBean);
        return BeanUtil.toPagedResult(schedulerMapper.selectByCondition(systemScheduler));
    }

    @Override
    public ResponseJson removeByIds(List<String> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return ServiceUtil.getResponseJson("删除失败，参数为空", SystemConstant.RESPONSE_ERROR);
        }

        SystemScheduler scheduler = new SystemScheduler();
        ModelUtil.deleteInit(scheduler);
        this.updateActiveFlagByPrimaryKeyList(ids, scheduler);
        return ServiceUtil.getResponseJson("删除成功", SystemConstant.RESPONSE_SUCCESS);
    }
}
