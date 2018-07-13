package com.common.quartz;


import com.base.quarz.mapper.SchedulerMapper;
import com.base.quarz.model.SystemScheduler;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统启动时，初始化定时任务
 */
@Component
public class InitializingJob implements InitializingBean {

    @Autowired
    private SchedulerMapper schedulerMapper;

    @Autowired
    private SchedulerManager schedulerManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        SystemScheduler systemScheduler = new SystemScheduler();
        systemScheduler.setStart(1);
        List<SystemScheduler> systemSchedulerList = schedulerMapper.selectByCondition(systemScheduler);
        if (CollectionUtils.isEmpty(systemSchedulerList)) {
            return;
        }

        for (SystemScheduler scheduler : systemSchedulerList) {
            schedulerManager.addScheduler(scheduler);
        }
    }
}
