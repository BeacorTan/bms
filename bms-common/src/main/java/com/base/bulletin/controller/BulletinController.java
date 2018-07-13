package com.base.bulletin.controller;

import com.base.bulletin.model.BulletinReadRecord;
import com.base.bulletin.model.SystemBulletin;
import com.base.bulletin.model.SystemBulletinSearchCondition;
import com.base.bulletin.service.SystemBulletinService;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
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
@RequestMapping(value = "/bulletin")
public class BulletinController {

    private Logger LOGGER = LoggerFactory.getLogger(BulletinController.class);

    @Autowired
    private SystemBulletinService systemBulletinService;

    @RequestMapping("main")
    public ModelAndView manage() {
        return new ModelAndView("bulletin/bulletin_main");
    }

    @RequestMapping("profile")
    public ModelAndView profile(String id, ModelMap modelMap) {
        if (StringUtils.isNotBlank(id) && !id.startsWith(SystemConstant.ADD_VIEW_TAB_ID_PREFIX)) {
            modelMap.put("systemBulletin", systemBulletinService.selectByPrimaryKey(id));
        }
        modelMap.put(SystemConstant.PROFILE_TAB_ID_ATTRIBUTE_NAME, id);
        return new ModelAndView("bulletin/bulletin_profile");
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public ResponseJson sysBulletinEdit(@RequestBody SystemBulletin systemBulletin) {
        return systemBulletinService.editBulletin(systemBulletin);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseJson SystemBulletinDel(@RequestBody List<String> ids) {
        return systemBulletinService.removeByIds(ids);
    }

    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<SystemBulletin> getBulletinPageList(PageBean pageBean, SystemBulletinSearchCondition condition) {
        return systemBulletinService.selectSystemBulletinPageList(pageBean, condition);
    }

    @RequestMapping(value = "/queryLimitThree")
    public List<SystemBulletin> queryLimitThree() {
        return systemBulletinService.queryLimitThree();
    }


    @RequestMapping("readRecord")
    public ModelAndView readRecord() {
        return new ModelAndView("bulletin/bulletin");
    }


    @RequestMapping("bulletinDetail")
    public ModelAndView bulletinDetail(String bulletinId, ModelMap modelMap) {
        try {
            systemBulletinService.saveReadLog(bulletinId);
            SystemBulletin bulletin = systemBulletinService.selectByPrimaryKey(bulletinId);
            modelMap.put("bulletin", bulletin);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("BulletinController.bulletinDetail()异常：", e);
        }
        return new ModelAndView("bulletin/bulletin_detail");
    }

    @RequestMapping(value = "queryReadList")
    public PagedResult<BulletinReadRecord> queryReadList(PageBean pageBean) {
        return systemBulletinService.readStatusByLoginName(pageBean);
    }
}
