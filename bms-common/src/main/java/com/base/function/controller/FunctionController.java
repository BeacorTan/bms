package com.base.function.controller;

import com.base.function.model.Function;
import com.base.function.service.FunctionService;
import com.common.framework.base.BaseModel;
import com.common.framework.util.IConUtils;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import com.common.model.TreeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能资源控制器
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

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseJson funcitonEdit(Function function) throws Exception {
        return functionService.functionEdit(function);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(String funCode, ModelMap modelMap) throws Exception {
        if (StringUtils.isNotBlank(funCode)) {
            modelMap.addAttribute("fun", functionService.selectByPrimaryKey(funCode));
        }
        return new ModelAndView("function/function_profile", modelMap);
    }

    @RequestMapping(value = "/tree/list", method = RequestMethod.GET)
    public List<TreeVO> tree(String roleCode) {
        return functionService.queryTree(roleCode);
    }

    /**
     * 菜单首页查询
     *
     * @param function
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PagedResult<Function> query(Function function, PageBean pageBean) {
        return functionService.queryByFunction(function, pageBean);
    }


//    @RequestMapping(value = "/info/{funId}", method = RequestMethod.GET)
//    public ResponseJson getFunInfo(@PathVariable(value = "funId") String funId) throws Exception {
//        return ServiceUtil.getResponseJson("查询成功", true, JSONArray.toJSON(functionMapper.selectByPrimaryKey(funId)));
//    }
//
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public ResponseJson addFunction(Function function) throws Exception {
//        ModelUtil.insertInit(function);
//        // 无需前端
//        Function resultFunction = functionService.insertSelective(function);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("id", resultFunction.getId());
//        jsonObject.put("pId", resultFunction.getParentId());
//        jsonObject.put("name", resultFunction.getFunName());
//        return ServiceUtil.getResponseJson("增加成功", true, jsonObject);
//    }

//    @RequestMapping(value = "", method = RequestMethod.PUT)
//    public ResponseJson updateFunction(Function function) throws Exception {
//        ModelUtil.updateInit(function);
//        Function resultFunction = functionService.updateByPrimaryKeySelective(function);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("id", resultFunction.getId());
//        jsonObject.put("pId", resultFunction.getParentId());
//        jsonObject.put("name", resultFunction.getFunName());
//        return ServiceUtil.getResponseJson("修改成功", true, jsonObject);
//    }
}
