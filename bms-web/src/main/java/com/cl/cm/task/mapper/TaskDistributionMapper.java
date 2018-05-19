package com.cl.cm.task.mapper;

import com.cl.cm.task.model.TaskCustomer;
import com.cl.cm.task.model.TaskDistributionRecordVO;
import com.cl.cm.task.model.TaskDistributionVO;
import com.cl.cm.task.model.TaskSearchConditionVO;
import com.cl.common.framework.base.BaseMapper;

import java.util.List;

/**
 * @author BoSongsh
 * @create 2018-04-23 15:02
 **/
public interface TaskDistributionMapper extends BaseMapper<TaskDistributionVO> {


    List<TaskDistributionRecordVO> selectCondition(TaskDistributionRecordVO TaskDistributionRecordVO);

    List<TaskCustomer> selectUserByCondition(TaskSearchConditionVO taskSearchConditionVO);

    int countTask(TaskSearchConditionVO taskSearchConditionVO);
}
