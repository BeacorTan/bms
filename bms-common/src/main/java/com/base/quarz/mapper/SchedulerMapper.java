package com.base.quarz.mapper;

import com.common.framework.base.BaseMapper;
import com.base.quarz.model.SystemScheduler;

import java.util.List;


public interface SchedulerMapper extends BaseMapper<SystemScheduler> {

    List<SystemScheduler> selectByCondition(SystemScheduler systemScheduler);

}
