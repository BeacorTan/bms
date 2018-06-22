package com.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页和登录基控制器
 */
@Controller
@RequestMapping("common")
public class CommonController {


    @RequestMapping(value = "tree")
    public ModelAndView depMain(String treeUrl, ModelMap modelMap) {
        modelMap.put("treeUrl", treeUrl);
        return new ModelAndView("common/tree");
    }


}
