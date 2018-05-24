package com.base.dept.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.dept.model.Department;
import com.base.dept.service.DepService;
import com.common.framework.base.BaseModel;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 部门控制器
 *
 */
@RestController
@RequestMapping(value = "dep")
public class DepController {


    @Resource
    private DepService depService;


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseJson addDep(Department department) throws Exception {
        ModelUtil.insertInit(department);
        Department resultDep = depService.insertSelective(department);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", resultDep.getId());
        jsonObject.put("parentCode", resultDep.getParentCode());
        jsonObject.put("depName", resultDep.getDepName());
        return ServiceUtil.getResponseJson("部门添加成功", true, jsonObject);
    }

    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public ResponseJson deleteDep(Department department) throws Exception {
        department.setActiveFlag(BaseModel.ACTIVE_FLAG_NO);//状态改为-1,代表逻辑删除
        ModelUtil.updateInit(department);
        depService.updateByPrimaryKeySelective(department);//修改操作
        return ServiceUtil.getResponseJson("删除成功", true, null);
    }


    @RequestMapping(value = "/json/list", method = RequestMethod.GET)
    public ResponseJson depJsonData(String roleCode) throws Exception {
        return ServiceUtil.getResponseJson("查询成功", true, JSONArray.toJSON(depService.getDepZtreeData(roleCode)));
    }


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView depMain() {
        return new ModelAndView("system/dep/dept_main");
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView addView(String depId, ModelMap modelMap) {
        modelMap.addAttribute("parentId", depId);
        return new ModelAndView("system/dep/dept_profile");
    }

}
