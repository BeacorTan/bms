package com.cl.cm.task.service.impl;

import com.cl.cm.task.mapper.TaskMapper;
import com.cl.cm.task.model.TaskVO;
import com.cl.cm.task.service.ITaskService;
import com.cl.common.framework.base.BaseMapper;
import com.cl.common.framework.base.BaseModel;
import com.cl.common.framework.base.BaseServiceImpl;
import com.cl.common.framework.util.ModelUtil;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
import com.cl.common.framework.util.ResponseJson;
import com.cl.common.framework.util.ServiceUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author BoSongsh
 * @create 2018-04-23 14:53
 **/
@Service
public class TaskServiceImpl extends BaseServiceImpl<TaskVO> implements ITaskService {

    private Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public ResponseJson edit(TaskVO task) {

        if (task == null) {
            return ServiceUtil.getResponseJson("编辑失败", false);
        }

        boolean flag = true;
        String msg = "编辑成功";
        String id = task.getId();

        try {
            if (StringUtils.isEmpty(id)) {
                ModelUtil.insertInit(task);
                taskMapper.insertSelective(task);
            } else {
                ModelUtil.updateInit(task);
                taskMapper.updateByPrimaryKeySelective(task);
            }
        } catch (Exception e) {
            flag = false;
            msg = e.getMessage();
        }
        return ServiceUtil.getResponseJson(msg, flag);

    }


    @Override
    public PagedResult<TaskVO> query(PageBean pageBean, TaskVO task) {

        if (task == null) {
            task = new TaskVO();
        }
        task.setActiveFlag(BaseModel.ACTIVE_FLAG_YES);
        try {
            return this.findPageList(pageBean.getPageNumber(), pageBean.getPageSize(), task);
        } catch (Exception e) {
            LOGGER.error("TaskServiceImpl.query()异常：{}", e);
        }
        return null;
    }

    @Override
    public BaseMapper getMapper() {
        return this.taskMapper;
    }
}
