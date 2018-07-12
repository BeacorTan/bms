package com.task.mapper;

import com.task.model.TaskCustomer;
import com.task.model.TaskDistributionRecordVO;
import com.task.model.TaskDistributionVO;
import com.task.model.TaskSearchConditionVO;
import com.common.framework.base.BaseMapper;

import java.util.List;

public interface TaskDistributionMapper extends BaseMapper<TaskDistributionVO> {


    List<TaskDistributionRecordVO> selectCondition(TaskDistributionRecordVO TaskDistributionRecordVO);

    List<TaskCustomer> selectUserByCondition(TaskSearchConditionVO taskSearchConditionVO);

    int countTask(TaskSearchConditionVO taskSearchConditionVO);
}
