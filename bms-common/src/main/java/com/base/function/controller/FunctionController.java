package com.base.function.controller;

import com.base.function.model.Function;
import com.base.function.service.FunctionService;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.IConUtils;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.model.TreeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 菜单图标
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/icons", method = RequestMethod.GET)
    public ModelAndView getIcons(String functionIconID, ModelMap modelMap) {
        modelMap.addAttribute("icons", IConUtils.getIcons());
        modelMap.addAttribute("functionIconID", functionIconID);
        ModelAndView modelAndView = new ModelAndView("function/function_icon");
        return modelAndView;
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseJson del(@RequestBody  List<String> ids)  {
        return functionService.updateActiveFlagByPrimaryKeyList(ids);
    }

    /**
     * 编辑数据
     *
     * @param function
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseJson functionEdit(@RequestBody Function function)  {
        return functionService.functionEdit(function);
    }

    /**
     * 编辑页面
     *
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(String id, ModelMap modelMap) {
        if (StringUtils.isNotBlank(id) && !id.startsWith(SystemConstant.ADD_VIEW_TAB_ID_PREFIX)) {
            modelMap.put("fun", functionService.selectByPrimaryKey(id));
        }
        modelMap.put(SystemConstant.PROFILE_TAB_ID_ATTRIBUTE_NAME, id);
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

}
