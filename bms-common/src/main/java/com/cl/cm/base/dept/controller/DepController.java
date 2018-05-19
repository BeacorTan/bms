package com.cl.cm.base.dept.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cl.cm.base.dept.model.Department;
import com.cl.cm.base.dept.service.DepService;
import com.cl.common.framework.base.BaseModel;
import com.cl.common.framework.util.ModelUtil;
import com.cl.common.framework.util.ResponseJson;
import com.cl.common.framework.util.ServiceUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/update/view", method = RequestMethod.GET)
    public ModelAndView depUpdatView(String depId, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("depInfo", depService.selectByPrimaryKey(depId));
        ModelAndView modelAndView = new ModelAndView("system/dep/dep_update", modelMap);
        return modelAndView;

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseJson addDep(Department department) throws Exception {
        ModelUtil.insertInit(department);
        Department resultDep = depService.insertSelective(department);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", resultDep.getId());
        jsonObject.put("parentId", resultDep.getParentId());
        jsonObject.put("depName", resultDep.getDepName());
        return ServiceUtil.getResponseJson("部门添加成功", true, jsonObject);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseJson deleteDep(Department department) throws Exception {
        department.setActiveFlag(BaseModel.ACTIVE_FLAG_NO);//状态改为-1,代表逻辑删除
        ModelUtil.updateInit(department);
        depService.updateByPrimaryKeySelective(department);//修改操作
        return ServiceUtil.getResponseJson("删除成功", true, null);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseJson updateDep(Department department) throws Exception {
        ModelUtil.updateInit(department);
        Department resultDep = depService.updateByPrimaryKeySelective(department);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", resultDep.getId());
        jsonObject.put("pId", resultDep.getParentId());
        jsonObject.put("name", resultDep.getDepName());
        return ServiceUtil.getResponseJson("部门修改成功", true, jsonObject);
    }

    @RequestMapping(value = "info/{id}")
    public ModelAndView depInfo(@PathVariable(value = "id") String id, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("depInfo", depService.selectByPrimaryKey(id));
        ModelAndView modelAndView = new ModelAndView("system/dep/dep_info", modelMap);
        return modelAndView;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView depMain() {
        return new ModelAndView("system/dep/dep_main");
    }

    @RequestMapping(value = "/add/view", method = RequestMethod.GET)
    public ModelAndView addView(String depId, ModelMap modelMap) {
        modelMap.addAttribute("parentId", depId);
        return new ModelAndView("system/dep/dep_add");
    }

    @RequestMapping(value = "/json/list", method = RequestMethod.GET)
    public ResponseJson depJsonData(String roleCode) throws Exception {
        return ServiceUtil.getResponseJson("查询成功", true, JSONArray.toJSON(depService.getDepZtreeData(roleCode)));
    }


    @RequestMapping(value = "/alert/list", method = RequestMethod.GET)
    public ModelAndView alterList() throws Exception {
        return new ModelAndView("system/dep/dep_list");
    }


}
