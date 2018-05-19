package com.task.mapper;

import com.task.model.TaskCustomer;
import com.task.model.TaskDistributionRecordVO;
import com.task.model.TaskDistributionVO;
import com.task.model.TaskSearchConditionVO;
import com.common.framework.base.BaseMapper;

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
