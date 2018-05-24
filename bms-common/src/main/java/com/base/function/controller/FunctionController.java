package com.base.function.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.function.mapper.FunctionMapper;
import com.base.function.service.FunctionService;
import com.base.function.model.Function;
import com.common.framework.util.IConUtils;
import com.common.framework.util.ModelUtil;
import com.common.framework.base.BaseModel;
import com.common.framework.util.ServiceUtil;
import com.common.framework.util.ResponseJson;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 功能资源控制器
 *
 *
 * @date 2017年04月07日
 */
@RestController
@RequestMapping(value = "/function")
public class FunctionController {
    @Resource
    private FunctionService functionService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView funMain() {
        ModelAndView modelAndView = new ModelAndView("function/function_main");
        return modelAndView;
    }

    @RequestMapping(value = "/add/view", method = RequestMethod.GET)
    public ModelAndView addView(String funId, ModelMap modelMap) {
        modelMap.addAttribute("parentId", funId);
        modelMap.addAttribute("icons", IConUtils.getIcons());
        ModelAndView modelAndView = new ModelAndView("function/function_add");
        return modelAndView;
    }

    @RequestMapping(value = "/icons", method = RequestMethod.GET)
    public ModelAndView getIcons(String parentWinName, ModelMap modelMap) {
        modelMap.addAttribute("icons", IConUtils.getIcons());
        modelMap.addAttribute("parentWinName", parentWinName);
        ModelAndView modelAndView = new ModelAndView("function/function_icon");
        return modelAndView;
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseJson deleteFun(Function function) throws Exception {
        function.setActiveFlag(BaseModel.ACTIVE_FLAG_NO);
        ModelUtil.updateInit(function);
        functionService.updateByPrimaryKeySelective(function);//修改操作
        return ServiceUtil.getResponseJson("删除成功", true, null);
    }

    @RequestMapping(value = "/update/view", method = RequestMethod.GET)
    public ModelAndView functionUpdatView(String functionId, ModelMap modelMap) throws Exception {
        modelMap.addAttribute("funInfo", functionService.selectByPrimaryKey(functionId));
        ModelAndView modelAndView = new ModelAndView("function/function_update", modelMap);
        return modelAndView;
    }

    @RequestMapping(value = "/json/list", method = RequestMethod.GET)
    public ResponseJson funJsonData(String roleCode) throws Exception {
        return ServiceUtil.getResponseJson("查询成功", true, JSONArray.toJSON(functionService.getFunctionZtreeData(roleCode)));
    }

    @Resource
    private FunctionMapper functionMapper;

    @RequestMapping(value = "/info/{funId}", method = RequestMethod.GET)
    public ResponseJson getFunInfo(@PathVariable(value = "funId") String funId) throws Exception {
        return ServiceUtil.getResponseJson("查询成功", true, JSONArray.toJSON(functionMapper.selectByPrimaryKey(funId)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseJson addFunction(Function function) throws Exception {
        ModelUtil.insertInit(function);
        // 无需前端
//        String funCode1 = "FUNCATIONCODE" + System.currentTimeMillis();
//        function.setFunCode(funCode);
        Function resultFunction = functionService.insertSelective(function);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", resultFunction.getId());
        jsonObject.put("pId", resultFunction.getParentId());
        jsonObject.put("name", resultFunction.getFunName());
        return ServiceUtil.getResponseJson("增加成功", true, jsonObject);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseJson updateFunction(Function function) throws Exception {
        ModelUtil.updateInit(function);
        Function resultFunction = functionService.updateByPrimaryKeySelective(function);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", resultFunction.getId());
        jsonObject.put("pId", resultFunction.getParentId());
        jsonObject.put("name", resultFunction.getFunName());
        return ServiceUtil.getResponseJson("修改成功", true, jsonObject);
    }
}
