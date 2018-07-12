package com.base.sys.controller;

import com.base.sys.model.SystemConfig;
import com.base.sys.service.SystemConfigService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 系统配置Controller
 */
@RestController
@RequestMapping(value = "/config")
public class SystemConfigController {

//    private Logger LOGGER = LoggerFactory.getLogger(SystemConfigController.class);

    @Autowired
    private SystemConfigService sysConfigService;

    /**
     * 系统配置列表页面
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView configMain() {
        return new ModelAndView("config/config_main");
    }

    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<SystemConfig> getsysConfigPageList(PageBean pageBean, SystemConfig sysConfig) throws Exception {
        return sysConfigService.selectSysConfigPageList(pageBean, sysConfig);
    }


    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseJson del(@RequestBody List<String> ids) {
        return  sysConfigService.removeByIds(ids);
    }

    @RequestMapping("profile")
    public ModelAndView profile(String id, ModelMap modelMap) {
        if (StringUtils.isNotBlank(id) && !id.startsWith("add")) {
            modelMap.put("sysConfig", sysConfigService.selectByPrimaryKey(id));
        }
        modelMap.put("tabId", id);
        return new ModelAndView("config/config_profile");
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST)
    public ResponseJson editConfig(@RequestBody SystemConfig sysConfig) {
        return sysConfigService.editConfig(sysConfig);
    }
}
