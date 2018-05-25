package com.base.dept.controller;

import com.alibaba.fastjson.JSONArray;
import com.base.dept.model.Department;
import com.base.dept.service.DeptService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门控制器
 */
@RestController
@RequestMapping(value = "dept")
public class DeptController {


    @Resource
    private DeptService depService;


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseJson edit(@RequestBody Department department) throws Exception {
        return depService.edit(department);
    }

    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public ResponseJson deleteDep(List<String> ids) throws Exception {
        return depService.remove(ids);
    }


    @RequestMapping(value = "/json/list", method = RequestMethod.GET)
    public PagedResult<Department> query(Department department, PageBean pageBean) throws Exception {
        return depService.query(department, pageBean);
    }


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView depMain() {
        return new ModelAndView("dep/dept_main");
    }

    @RequestMapping(value = "/profile")
    public ModelAndView profile(String id, ModelMap modelMap) {
        modelMap.addAttribute("parentId", id);
        return new ModelAndView("dep/dept_profile");
    }

}
