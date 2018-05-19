package com.cl.cm.base.sys.controller;

import java.util.List;

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

import com.cl.cm.base.sys.model.CrmSysConfig;
import com.cl.cm.base.sys.service.CrmSysConfigService;
import com.cl.common.framework.util.ModelUtil;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
import com.cl.common.framework.util.ResponseJson;
import com.cl.common.framework.util.ServiceUtil;

/**
 * 系统配置Controller
 * @author XingyuLi
 *
 */
@RestController
@RequestMapping(value = "/crmSysConfig")
public class CrmSysConfigController
{
	private Logger LOGGER = LoggerFactory.getLogger(CrmSysConfigController.class);
	@Autowired
	private CrmSysConfigService crmSysConfigService;
	
	/**
	 * 系统配置列表页面
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView getSysConfigListView()
	{
		return new ModelAndView("system/config/sysConfig_manage");
	}
	
	 @RequestMapping(value = "/page/list", method = RequestMethod.GET)
	 @ResponseBody
	 public PagedResult<CrmSysConfig> getCrmSysConfigPageList(PageBean pageBean, CrmSysConfig crmSysConfig) throws Exception {
		 return crmSysConfigService.selectCrmSysConfigPageList(pageBean, crmSysConfig);
     } 
	 
	 
	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public ResponseJson del(@RequestBody List<String> idList) {
		 CrmSysConfig crmSysConfig = new CrmSysConfig();
        ModelUtil.deleteInit(crmSysConfig);
        boolean isSuccess = false;
        try {
            isSuccess = crmSysConfigService.updateActiveFlagByPrimaryKeyList(idList, crmSysConfig);
        } catch (Exception e) {
            LOGGER.error("CrmSysConfigController.del()异常：{}", e);
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
	        	CrmSysConfig crmSysConfig = null;
	            try {
	            	crmSysConfig = crmSysConfigService.selectByPrimaryKey(id);
	            } catch (Exception e) {
	                LOGGER.error("CrmSysConfigController.sysConfigEdit()异常：{}", e);
	            }
	            modelMap.put("crmSysConfig", crmSysConfig);
	            return new ModelAndView("system/config/sysConfig_manageForm", modelMap);
	        }
	        return new ModelAndView("system/config/sysConfig_manageForm");
	    }

	 @RequestMapping(value = "sysConfigEdit", method = RequestMethod.POST)
     public ResponseJson sysConfigEdit(CrmSysConfig crmSysConfig) {
        return crmSysConfigService.sysConfigEditService(crmSysConfig);
     }

	
}
