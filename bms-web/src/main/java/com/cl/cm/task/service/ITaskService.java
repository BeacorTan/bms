package com.cl.cm.task.service;

import com.cl.cm.task.model.TaskVO;
import com.cl.common.framework.base.BaseService;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
import com.cl.common.framework.util.ResponseJson;

/**
 * 任务service
 *
 * @author BoSongsh
 * @create 2018-04-23 14:52
 **/
public interface ITaskService extends BaseService<TaskVO> {

    ResponseJson edit(TaskVO task);

    PagedResult<TaskVO> query(PageBean pageBean, TaskVO task);

}
