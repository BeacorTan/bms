package com.task.controller;

import com.task.model.TaskDistributionRecordVO;
import com.task.model.TaskSearchConditionVO;
import com.task.model.TaskVO;
import com.task.service.ITaskDistributionService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 任务分配
 *
 * @author BoSongsh
 * @create 2018-04-20 17:20
 **/
@RestController
@RequestMapping(value = "/task/distribution")
public class TaskDistributionController {

    private Logger LOGGER = LoggerFactory.getLogger(TaskDistributionController.class);


    @Autowired
    private ITaskDistributionService taskDistributionService;

    @RequestMapping("main")
    public ModelAndView manage() {
        return new ModelAndView("module/task/task_distribution");
    }


    @RequestMapping("profile")
    public ModelAndView profile(String id, ModelMap modelMap) {
        if (StringUtils.isNotBlank(id)) {
            TaskVO task = null;
            try {

            } catch (Exception e) {
                LOGGER.error("TaskManageController.profile()异常：{}", e);
            }
            modelMap.put("task", task);
            return new ModelAndView("module/task/task_profile", modelMap);
        }
        return new ModelAndView("module/task/task_profile");
    }

//
//    @RequestMapping(value = "profile", method = RequestMethod.POST)
//    public ResponseJson profile(TaskVO task) {
//        return taskService.edit(task);
//    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseJson upload(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("taskID") String taskID) {
        return taskDistributionService.distribute(file, taskID);
    }

    @RequestMapping(value = "/query")
    @ResponseBody
    public PagedResult<TaskDistributionRecordVO> getUserPageList(PageBean pageBean, TaskDistributionRecordVO taskDistributionRecordVO) throws Exception {
        return taskDistributionService.query(pageBean, taskDistributionRecordVO);
    }

    @RequestMapping(value = "/countTask")
    @ResponseBody
    public Integer countTask() {
        // 只统计状态为0（待处理）的任务总数
        return taskDistributionService.countTask(new TaskSearchConditionVO(0));
    }
//
//
//    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
//    public ResponseJson del(@RequestBody List<String> idList) {
//        TaskVO task = new TaskVO();
//        ModelUtil.deleteInit(task);
//        boolean isSuccess = false;
//        try {
//            isSuccess = taskService.updateActiveFlagByPrimaryKeyList(idList, task);
//        } catch (Exception e) {
//            LOGGER.error("TaskManageController.del()异常：{}", e);
//        }
//        if (isSuccess) {
//            return ServiceUtil.getResponseJson("删除成功", isSuccess);
//        } else {
//            return ServiceUtil.getResponseJson("删除失败", isSuccess);
//        }
//    }
}
