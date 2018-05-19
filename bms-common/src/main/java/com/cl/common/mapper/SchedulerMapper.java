package com.cl.common.mapper;

import com.cl.common.model.SystemScheduler;
import com.cl.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 *
 */

public interface SchedulerMapper extends BaseMapper<SystemScheduler>{
    /*
   * 根据主键改变任务的开关状态
   * */
    public int updateStatusByPrimaryKey(@Param(value = "key") String key, @Param(value = "isStart")String isStart)throws Exception;
    /*
  * 根据检索条件查询定时任务信息记录
  * */
    public List<Map> selectByCondition(SystemScheduler systemScheduler)throws Exception;

}
