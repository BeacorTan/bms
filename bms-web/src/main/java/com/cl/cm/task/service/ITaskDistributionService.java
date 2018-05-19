package com.cl.cm.task.service;

import com.cl.cm.task.model.TaskCustomer;
import com.cl.cm.task.model.TaskDistributionRecordVO;
import com.cl.cm.task.model.TaskDistributionVO;
import com.cl.cm.task.model.TaskSearchConditionVO;
import com.cl.common.framework.base.BaseService;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
import com.cl.common.framework.util.ResponseJson;
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
