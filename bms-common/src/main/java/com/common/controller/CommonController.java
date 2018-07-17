package com.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("common")
public class CommonController {


    @RequestMapping(value = "tree")
    public ModelAndView depMain(@RequestParam(value = "treeUrl") String treeUrl,@RequestParam(value = "id") String id, ModelMap modelMap) {
        modelMap.put("treeUrl", treeUrl);
        modelMap.put("id", id);
        return new ModelAndView("common/tree");
    }


}
