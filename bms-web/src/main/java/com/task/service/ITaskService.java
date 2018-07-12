package com.task.service;

import com.task.model.TaskVO;
import com.common.framework.base.BaseService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

public interface ITaskService extends BaseService<TaskVO> {

    ResponseJson edit(TaskVO task);

    PagedResult<TaskVO> query(PageBean pageBean, TaskVO task);

}
