package com.task.controller;

import com.task.model.TaskVO;
import com.task.service.ITaskService;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/task/manage")
public class TaskManageController {

    private Logger LOGGER = LoggerFactory.getLogger(TaskManageController.class);


    @Autowired
    private ITaskService taskService;

    @RequestMapping("main")
    public ModelAndView manage() {
        return new ModelAndView("module/task/task_manage");
    }


    @RequestMapping("profile")
    public ModelAndView profile(String id, ModelMap modelMap) {

        if (StringUtils.isNotBlank(id)) {
            TaskVO task = null;
            try {
                task = taskService.selectByPrimaryKey(id);
            } catch (Exception e) {
                LOGGER.error("TaskManageController.profile()异常：{}", e);
            }
            modelMap.put("task", task);
            return new ModelAndView("module/task/task_profile", modelMap);
        }
        return new ModelAndView("module/task/task_profile");
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public ResponseJson profile(TaskVO task) {
        return taskService.edit(task);
    }

    @RequestMapping(value = "/query")
    @ResponseBody
    public PagedResult<TaskVO> getUserPageList(PageBean pageBean, TaskVO task) throws Exception {
        return taskService.query(pageBean, task);
    }


    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public ResponseJson del(@RequestBody List<String> idList) {
        TaskVO task = new TaskVO();
        ModelUtil.deleteInit(task);
        boolean isSuccess = false;
        try {
            isSuccess = taskService.updateActiveFlagByPrimaryKeyList(idList, task);
        } catch (Exception e) {
            LOGGER.error("TaskManageController.del()异常：{}", e);
        }
        if (isSuccess) {
            return ServiceUtil.getResponseJson("删除成功", isSuccess);
        } else {
            return ServiceUtil.getResponseJson("删除失败", isSuccess);
        }
    }
}
