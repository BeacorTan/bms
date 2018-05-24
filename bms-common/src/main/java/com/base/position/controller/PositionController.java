package com.base.position.controller;

import com.base.position.model.Position;
import com.base.position.service.PositionService;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ServiceUtil;
import com.common.framework.util.ResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

import static com.common.framework.util.ServiceUtil.getResponseJson;

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


    @RequestMapping(value = "/profileView", method = RequestMethod.GET)
    public ModelAndView profileView(String id, ModelMap modelMap) {
        if (!StringUtils.isEmpty(id)) {
            modelMap.put("position", positionService.queryById(id));
        }
        return new ModelAndView("position/position_profile", modelMap);
    }

    /**
     * 分页查询用户集合
     *
     * @return UserSample
     */
    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<Position> getUserPageList(PageBean pageBean, Position position) {
        PagedResult<Position> result = null;
        try {
            result = positionService.selectPageList(pageBean, position);
        } catch (Exception e) {
            LOGGER.error("查询失败", e);
        }
        return result;
    }

    /*@RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseJson query(@RequestBody Position position) {
        if (position == null) {
            return getResponseJson(SystemConstant.ADD_SUCCESS, false, "传入参数为空，添加失败！");
        }
        try {

        } catch (Exception e) {
            LOGGER.error("查询职位异常：{}", e);
            return getResponseJson(SystemConstant.ADD_SUCCESS, false, null);
        }
        return getResponseJson(SystemConstant.ADD_SUCCESS, true, null);
    }*/



    /**
     * 增加角色
     *
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseJson edit(@RequestBody Position position) {

        if (position == null) {
            return ServiceUtil.getResponseJson(SystemConstant.ADD_SUCCESS, false, "传入参数为空，添加失败！");
        }
        String id = position.getId();
        try {
            if (StringUtils.isEmpty(id)) {
                positionService.insert(position);
            } else {
                positionService.update(position);
            }
        } catch (Exception e) {
            LOGGER.error("新增职位异常：{}", e);
            return ServiceUtil.getResponseJson(SystemConstant.ADD_SUCCESS, false, null);
        }
        return ServiceUtil.getResponseJson(SystemConstant.ADD_SUCCESS, true, null);
    }


    /**
     * 删除职位信息
     *
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ResponseJson del(String id) {
        try {
            positionService.delete(new Position(id));
        } catch (Exception e) {
            LOGGER.error("删除职位异常：{}", e);
            return ServiceUtil.getResponseJson(SystemConstant.UPDATE_SUCCESS, false, null);
        }
        return ServiceUtil.getResponseJson(SystemConstant.UPDATE_SUCCESS, true, null);
    }


    /**
     * 批量删除角色
     *
     * @param idList 用户集合
     */
    @RequestMapping(value = "/batchDel", method = RequestMethod.DELETE)
    public ResponseJson batchDel(@RequestBody List<String> idList) throws Exception {
//        boolean isSuccess = roleService.deleteByPrimaryKeyList(idList);
        return ServiceUtil.getResponseJson("删除成功", true, null);
    }

}
