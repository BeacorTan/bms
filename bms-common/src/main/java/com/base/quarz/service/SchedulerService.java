package com.base.quarz.service;

import com.common.framework.base.BaseService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.base.quarz.model.SystemScheduler;

import java.util.List;

/**
 * 调度接口
 *
 * @date 2017年04月11日
 */

public interface SchedulerService extends BaseService<SystemScheduler> {


    ResponseJson changeStatus(List<SystemScheduler> systemSchedulers, Integer isStart);

    ResponseJson editScheduler(SystemScheduler scheduler);

    /**
     * 按照检索条件分页查询所有任务信息
     */
    PagedResult<SystemScheduler> findPageListByName(PageBean pageBean, SystemScheduler systemScheduler);

    ResponseJson removeByIds(List<String> ids);
}
