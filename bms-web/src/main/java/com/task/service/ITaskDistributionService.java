package com.task.service;

import com.task.model.TaskCustomer;
import com.task.model.TaskDistributionRecordVO;
import com.task.model.TaskDistributionVO;
import com.task.model.TaskSearchConditionVO;
import com.common.framework.base.BaseService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import org.springframework.web.multipart.MultipartFile;

/**
 * 任务service
 *
 * @author BoSongsh
 * @create 2018-04-23 14:52
 **/
public interface ITaskDistributionService extends BaseService<TaskDistributionVO> {

    ResponseJson distribute(MultipartFile file, String taskID);

    PagedResult<TaskDistributionRecordVO> query(PageBean pageBean, TaskDistributionRecordVO task);

    PagedResult<TaskCustomer> queryUser(PageBean pageBean, TaskSearchConditionVO taskSearchConditionVO);

    int countTask(TaskSearchConditionVO taskSearchConditionVO);
}
