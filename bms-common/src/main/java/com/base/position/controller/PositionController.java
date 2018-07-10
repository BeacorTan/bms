package com.base.position.controller;

import com.base.position.model.Position;
import com.base.position.service.PositionService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 职位管理
 *
 * @create 2018-01-11 17:05
 **/
@RestController
@RequestMapping(value = "/position")
public class PositionController {

    private Logger LOGGER = LoggerFactory.getLogger(PositionController.class);

    @Resource
    private PositionService positionService;

    /**
     * 角色管理主页面
     *
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView userMain() {
        return new ModelAndView("position/position_main");
    }


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(String id, ModelMap modelMap) {
        if (StringUtils.isNotBlank(id) && !id.startsWith("add")) {
            modelMap.put("position", positionService.queryById(id));
        }
        modelMap.put("tabId", id);
        return new ModelAndView("position/position_profile", modelMap);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseJson editPosition(@RequestBody Position position) {
        return positionService.editPosition(position);
    }


    /**
     * 分页查询用户集合
     *
     * @return UserSample
     */
    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<Position> getUserPageList(PageBean pageBean, Position position) {
        return positionService.selectPageList(pageBean, position);
    }


    /**
     * 删除职位信息
     *
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseJson del(@RequestBody List<String> ids) {
        return positionService.updateActiveFlagByPrimaryKeyList(ids);
    }

}
