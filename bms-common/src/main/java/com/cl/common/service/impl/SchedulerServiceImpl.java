package com.cl.common.service.impl;

import com.cl.common.framework.base.BaseMapper;
import com.cl.common.framework.base.BaseServiceImpl;
import com.cl.common.framework.util.BeanUtil;
import com.cl.common.framework.util.HelpUtils;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
import com.cl.common.framework.util.ResponseJson;
import com.cl.common.framework.util.ServiceUtil;
import com.cl.common.mapper.SchedulerMapper;
import com.cl.common.model.SystemScheduler;
import com.cl.common.service.SchedulerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 调度接口
 *
 *
 * @date 2017年04月11日
 */
@Service
public class SchedulerServiceImpl extends BaseServiceImpl<SystemScheduler> implements SchedulerService{

    @Resource
    private SchedulerMapper schedulerMapper;

//    @Resource
//    private SchedulerManager schedulerManager;

    @Override
    public BaseMapper getMapper() {
        return schedulerMapper;
    }

    @Override
    public ResponseJson changeStatus(List<String> idList, String isStart)throws Exception{
        if(HelpUtils.isNotEmpty(idList)){
            for (String id:idList) {
                schedulerMapper.updateStatusByPrimaryKey(id,isStart);
            }
            if(isStart.equals("1")){
                return ServiceUtil.getResponseJson("任务开启成功",true,null);
            }else{
                return ServiceUtil.getResponseJson("任务关闭成功",true,null);
            }
        }
        return ServiceUtil.getResponseJson("操作失败",false,null);
    }

    @Override
    public PagedResult<Map> findPageListByName(PageBean pageBean, SystemScheduler  systemScheduler)throws Exception{
        //注册所有任务到scheduler里面,并根据scheduler的状态
        List<SystemScheduler> schedulerList = schedulerMapper.selectAll();
        if(HelpUtils.isNotEmpty(schedulerList)){
            for(SystemScheduler scheduler :schedulerList){
                //schedulerManager.addScheduler(scheduler);
            }
        }
        ServiceUtil.startPage(pageBean);
        return BeanUtil.toPagedResult(schedulerMapper.selectByCondition(systemScheduler));
    }


}
