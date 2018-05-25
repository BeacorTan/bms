package com.base.dept.controller;

import com.base.dept.model.Department;
import com.base.dept.service.DeptService;
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

    private Logger LOGGER = LoggerFactory.getLogger(DeptController.class);

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
        if (StringUtils.isNotBlank(id)) {
            try {
                modelMap.addAttribute("dept", depService.selectByPrimaryKey(id));
            } catch (Exception e) {
                LOGGER.error("DeptController.profile()异常：{}", e);
            }
        }
        return new ModelAndView("dep/dept_profile");
    }

}
