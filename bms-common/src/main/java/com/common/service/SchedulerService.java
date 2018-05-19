package com.common.service;

import com.common.framework.util.PageBean;
import com.common.model.SystemScheduler;
import com.common.framework.base.BaseService;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

import java.util.List;
import java.util.Map;

/**
 * 调度接口
 *
 *
 * @date 2017年04月11日
 */

public interface SchedulerService extends BaseService<SystemScheduler> {
    public ResponseJson changeStatus(List<String> idList, String isStart)throws Exception;

    /**
     * 按照检索条件分页查询所有任务信息
     *
     */
    public PagedResult<Map> findPageListByName(PageBean pageBean, SystemScheduler  systemScheduler)throws Exception;

}
