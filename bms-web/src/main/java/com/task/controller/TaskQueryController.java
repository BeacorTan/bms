package com.task.controller;

import com.task.model.TaskCustomer;
import com.task.model.TaskSearchConditionVO;
import com.task.service.ITaskDistributionService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 任务管理
 *
 * @author BoSongsh
 * @create 2018-04-20 17:20
 **/
@RestController
@RequestMapping(value = "/task/query")
public class TaskQueryController {

    private Logger LOGGER = LoggerFactory.getLogger(TaskQueryController.class);


    @Autowired
    private ITaskDistributionService taskDistributionService;

    @RequestMapping("main")
    public ModelAndView manage() {
        return new ModelAndView("module/task/task_query");
    }

    @RequestMapping(value = "/queryUser")
    @ResponseBody
    public PagedResult<TaskCustomer> queryUser(PageBean pageBean, TaskSearchConditionVO taskSearchConditionVO){
        return taskDistributionService.queryUser(pageBean,taskSearchConditionVO);
    }
}
