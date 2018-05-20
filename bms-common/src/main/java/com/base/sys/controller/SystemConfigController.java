package com.base.sys.controller;

import com.base.sys.model.SystemConfig;
import com.base.sys.service.SystemConfigService;
import com.common.framework.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 系统配置Controller
 *
 *
 */
@RestController
@RequestMapping(value = "/systemConfig")
public class SystemConfigController
{
	private Logger LOGGER = LoggerFactory.getLogger(SystemConfigController.class);
	@Autowired
	private SystemConfigService sysConfigService;
	
	/**
	 * 系统配置列表页面
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView getSysConfigListView()
	{
		return new ModelAndView("system/config/systemConfig_manage");
	}
	
	 @RequestMapping(value = "/page/list", method = RequestMethod.GET)
	 @ResponseBody
	 public PagedResult<SystemConfig> getsysConfigPageList(PageBean pageBean, SystemConfig sysConfig) throws Exception {
		 return sysConfigService.selectSysConfigPageList(pageBean, sysConfig);
     } 
	 
	 
	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public ResponseJson del(@RequestBody List<String> idList) {
		 SystemConfig sysConfig = new SystemConfig();
        ModelUtil.deleteInit(sysConfig);
        boolean isSuccess = false;
        try {
            isSuccess = sysConfigService.updateActiveFlagByPrimaryKeyList(idList, sysConfig);
        } catch (Exception e) {
            LOGGER.error("sysConfigController.del()异常：{}", e);
        }
        if (isSuccess) {
            return ServiceUtil.getResponseJson("删除成功", isSuccess);
        } else {
            return ServiceUtil.getResponseJson("删除失败", isSuccess);
        }
    }
	 
	 @RequestMapping("sysConfigEdit")
	 public ModelAndView sysConfigEdit(String id, ModelMap modelMap) {
	        if (StringUtils.isNotBlank(id)) {
	        	SystemConfig sysConfig = null;
	            try {
	            	sysConfig = sysConfigService.selectByPrimaryKey(id);
	            } catch (Exception e) {
	                LOGGER.error("sysConfigController.sysConfigEdit()异常：{}", e);
	            }
	            modelMap.put("sysConfig", sysConfig);
	            return new ModelAndView("system/config/systemConfig_manageForm", modelMap);
	        }
	        return new ModelAndView("system/config/systemConfig_manageForm");
	    }

	 @RequestMapping(value = "sysConfigEdit", method = RequestMethod.POST)
     public ResponseJson sysConfigEdit(SystemConfig sysConfig) {
        return sysConfigService.updateSysConfig(sysConfig);
     }
}
