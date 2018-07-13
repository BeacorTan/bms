package com.base.quarz.controller;

import com.common.framework.constant.SystemConstant;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.base.quarz.model.SystemScheduler;
import com.base.quarz.service.SchedulerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

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
        ModelAndView modelAndView = new ModelAndView("scheduler/scheduler_main");
        return modelAndView;
    }
    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public ModelAndView profile(String id, ModelMap modelMap) throws Exception{
//        SystemScheduler systemScheduler =  schedulerService.selectByPrimaryKey(id);
//        modelMap.addAttribute("schedulerInfo",systemScheduler);
//        ModelAndView modelAndView = new ModelAndView("scheduler/scheduler_update");
        if (StringUtils.isNotBlank(id) && !id.startsWith(SystemConstant.ADD_VIEW_TAB_ID_PREFIX)) {
            modelMap.put("schedulerInfo",schedulerService.selectByPrimaryKey(id));
        }
        modelMap.put(SystemConstant.PROFILE_TAB_ID_ATTRIBUTE_NAME, id);
        return new ModelAndView("scheduler/scheduler_profile",modelMap);
    }

    /**
     * 查询所有定时任务集合,以表格形式分页展示
     * @return
     */
    @RequestMapping(value="/page/list",method = RequestMethod.GET)
    public PagedResult<SystemScheduler> getSchedulerPageList(PageBean pageBean, SystemScheduler systemScheduler){
        return schedulerService.findPageListByName(pageBean, systemScheduler);
    }
    /**
     * 修改定时器
     * @return
     */
    @RequestMapping(value="profile",method = RequestMethod.POST)
    public ResponseJson profile(@RequestBody SystemScheduler systemScheduler){
        return schedulerService.editScheduler(systemScheduler);
    }


    @RequestMapping(value="del",method = RequestMethod.POST)
    public ResponseJson remove(@RequestBody List<String> ids){
        return schedulerService.removeByIds(ids);
    }


    /**
     * 改变任务的状态
     * @return
     */
    @RequestMapping(value = "/status/{isStart}")
    public ResponseJson changeStatus(@PathVariable("isStart")Integer isStart , @RequestBody List<SystemScheduler> systemSchedulers) {
        return schedulerService.changeStatus(systemSchedulers,isStart);
    }
}
