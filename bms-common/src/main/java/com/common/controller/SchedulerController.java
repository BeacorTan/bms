package com.common.controller;

import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import com.common.model.SystemScheduler;
import com.common.service.SchedulerService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 调度控制器
 *
 *
 * @date 2017年04月11日
 */
@RestController
@RequestMapping(value = "/scheduler")
public class SchedulerController {
    @Resource
    private SchedulerService schedulerService;

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public ModelAndView schedulerMain(){
        ModelAndView modelAndView = new ModelAndView("system/scheduler/scheduler_main");
        return modelAndView;
    }
    @RequestMapping(value = "/update/view/{schedulerId}",method = RequestMethod.GET)
    public ModelAndView schedulerUpdateView(@PathVariable(value = "schedulerId")String schedulerId, ModelMap modelMap) throws Exception{
        SystemScheduler systemScheduler =  schedulerService.selectByPrimaryKey(schedulerId);
        modelMap.addAttribute("schedulerInfo",systemScheduler);
        ModelAndView modelAndView = new ModelAndView("system/scheduler/scheduler_update");
        return modelAndView;
    }

    /**
     * 查询所有定时任务集合,以表格形式分页展示
     * @return
     */
    @RequestMapping(value="/page/list",method = RequestMethod.GET)
    public PagedResult<Map> getSchedulerPageList(PageBean pageBean, SystemScheduler systemScheduler)throws Exception{
        return schedulerService.findPageListByName(pageBean, systemScheduler);
    }
    /**
     * 修改定时器
     * @return
     */
    @RequestMapping(value="",method = RequestMethod.POST)
    public ResponseJson updateScheduler(SystemScheduler systemScheduler)throws Exception{
        schedulerService.updateByPrimaryKeySelective(systemScheduler);
        return ServiceUtil.getResponseJson("修改成功",true,null);
    }
    /**
     * 改变任务的状态
     * @return
     */
    @RequestMapping(value = "/status/{isStart}")
    public ResponseJson changeStatus(@PathVariable("isStart")String isStart , @RequestBody List<String> idList) throws Exception{
        return schedulerService.changeStatus(idList,isStart);
    }
}
