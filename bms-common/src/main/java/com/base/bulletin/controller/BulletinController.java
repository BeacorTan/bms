package com.base.bulletin.controller;

import java.util.List;

import com.base.bulletin.model.BulletinReadRecord;
import com.base.bulletin.model.SystemBulletin;
import com.common.framework.util.ServiceUtil;
import com.common.utils.DateUtils;
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

import com.base.bulletin.service.SystemBulletinService;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;


/**
 * 公告
 *
 * @author BoSongsh
 * @create 2018-05-07 18:05
 **/
@RestController
@RequestMapping(value = "/bulletin")
public class BulletinController {

    private Logger LOGGER = LoggerFactory.getLogger(BulletinController.class);

    @Autowired
    private SystemBulletinService systemBulletinService;

    @RequestMapping("main")
    public ModelAndView manage() {
        return new ModelAndView("system/bulletin/systemBulletinList");
    }


    @RequestMapping("readRecord")
    public ModelAndView readRecord() {
        return new ModelAndView("system/bulletin/bulletin");
    }


    @RequestMapping("bulletinDetail")
    public ModelAndView bulletinDetail(String bulletinId, ModelMap modelMap) {
        try {
            systemBulletinService.saveReadLog(bulletinId);
            SystemBulletin bulletin = systemBulletinService.selectBulletinByPrimaryKey(bulletinId);
            modelMap.put("bulletin", bulletin);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("BulletinController.bulletinDetail()异常：", e);
        }
        return new ModelAndView("system/bulletin/bulletin_detail");
    }

    @RequestMapping(value = "queryReadList")
    public PagedResult<BulletinReadRecord> queryReadList(PageBean pageBean) {
        return systemBulletinService.readStatusByLoginName(pageBean);
    }


    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<SystemBulletin> getBulletinPageList(PageBean pageBean, SystemBulletin systemBulletin) throws Exception {
        try {
            if (StringUtils.isNotBlank(systemBulletin.getBeginDateText())) {
                systemBulletin.setBeginDate(DateUtils.formatDate(systemBulletin.getBeginDateText()));
            }
            if (StringUtils.isNotBlank(systemBulletin.getEndDateText())) {
                systemBulletin.setEndDate(DateUtils.formatDate(systemBulletin.getEndDateText()));
                systemBulletin.setEndDate(DateUtils.getMaximumDay(systemBulletin.getEndDate()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("BulletinController.getBulletinPageList()日期格式转换出现异常!");
        }

        PagedResult<SystemBulletin> page = systemBulletinService.selectSystemBulletinPageList(pageBean, systemBulletin);
        return page;
    }

    @RequestMapping(value = "/queryLimitThree")
    public List<SystemBulletin> queryLimitThree() {
        return systemBulletinService.queryLimitThree();
    }


    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public ResponseJson SystemBulletinDel(@RequestBody List<String> idList) {
        SystemBulletin systemBulletin = new SystemBulletin();
        ModelUtil.deleteInit(systemBulletin);
        boolean isSuccess = false;
        try {
            isSuccess = systemBulletinService.updateActiveFlagByPrimaryKeyList(idList, systemBulletin);
        } catch (Exception e) {
            LOGGER.error("sysConfigController.del()异常：{}", e);
        }
        if (isSuccess) {
            return ServiceUtil.getResponseJson("删除成功", isSuccess);
        } else {
            return ServiceUtil.getResponseJson("删除失败", isSuccess);
        }
    }

    @RequestMapping("bulletinEdit")
    public ModelAndView sysBulletinEdit(String id, ModelMap modelMap) {
        if (StringUtils.isNotBlank(id)) {
            SystemBulletin systemBulletin = null;
            try {
                systemBulletin = systemBulletinService.selectBulletinByPrimaryKey(id);
            } catch (Exception e) {
                LOGGER.error("BulletinController.sysBulletinEdit()异常：{}", e);
            }
            modelMap.put("systemBulletin", systemBulletin);
            return new ModelAndView("system/bulletin/systemBulletinForm", modelMap);
        }
        return new ModelAndView("system/bulletin/systemBulletinForm");
    }

    @RequestMapping(value = "bulletinEdit", method = RequestMethod.POST)
    public ResponseJson sysBulletinEdit(SystemBulletin systemBulletin) {
        return systemBulletinService.systemBulletinEditService(systemBulletin);
    }
}
