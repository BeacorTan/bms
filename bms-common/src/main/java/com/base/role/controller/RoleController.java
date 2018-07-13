package com.base.role.controller;

import com.base.role.model.Role;
import com.base.role.model.RoleVO;
import com.base.role.service.RoleService;
import com.base.user.model.UserRoleVO;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理
 *
 **/
@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 角色管理主页面
     *
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView userMain() {
        return new ModelAndView("role/role_main");
    }

    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<Role> getRolePageList(PageBean pageBean, Role role) throws Exception {
        return roleService.selectPageList(pageBean, role);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<UserRoleVO> getUserPageList(PageBean pageBean, String loginName) throws Exception {
        return roleService.selectPageList(pageBean, loginName);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(String id, ModelMap modelMap) {
        if (StringUtils.isNotBlank(id) && !id.startsWith(SystemConstant.ADD_VIEW_TAB_ID_PREFIX)) {
            modelMap.put("role", roleService.selectByPrimaryKey(id));
        }
        modelMap.put(SystemConstant.PROFILE_TAB_ID_ATTRIBUTE_NAME, id);
        return new ModelAndView("role/role_profile", modelMap);
    }


    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseJson editRole(@RequestBody RoleVO roleVO) {
        return roleService.editRole(roleVO);
    }

    /**
     * 批量删除角色
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseJson del(@RequestBody List<String> ids) {
        return roleService.removeRoleByKeys(ids);
    }
}
