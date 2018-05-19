package com.base.role.controller;

import com.base.role.model.Role;
import com.base.role.model.RoleVO;
import com.base.role.service.RoleService;
import com.base.user.model.UserRoleVO;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.HelpUtils;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ServiceUtil;
import com.common.framework.util.ResponseJson;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

import static com.common.framework.util.ServiceUtil.getResponseJson;

/**
 * 角色管理
 *
 * @author BoSongsh
 * @create 2018-01-11 17:05
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
        return new ModelAndView("system/role/role_list");
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

    /**
     * 添加、修改  roleId==‘insert’表示新增
     *
     * @return
     */
    @RequestMapping(value = "/add/view/{roleId}", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable(value = "roleId") String roleId, ModelMap modelMap) throws Exception {

        if (null != roleId && !"".equals(roleId) && !"insert".equals(roleId)) {
            Role role = roleService.selectByPrimaryKey(roleId);
            if (HelpUtils.isNotEmpty(role)) {
                modelMap.put("roleInfo", role);
                return new ModelAndView("system/role/role_add", modelMap);
            } else {
                modelMap.put("status", SystemConstant.ERROR_CODE_500);
                modelMap.put("error", "请填写准确的参数!");
                return new ModelAndView(SystemConstant.ERROR_PAGE, modelMap);
            }
        } else {
            return new ModelAndView("system/role/role_add");
        }

    }

    /**
     * 增加角色
     *
     * @param roleVO
     * @return
     */
    @RequestMapping(value = "/roleAdd", method = RequestMethod.POST)
    public ResponseJson roleAdd(@RequestBody RoleVO roleVO) {

        try {
            roleService.addRoleAndRoleFunction(roleVO);
        } catch (Exception e) {
            e.printStackTrace();
            return getResponseJson(SystemConstant.ADD_SUCCESS, false, null);
        }
        return getResponseJson(SystemConstant.ADD_SUCCESS, true, null);
    }

    /**
     * 修改角色信息
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/roleEdit", method = RequestMethod.POST)
    public ResponseJson updateRole(@RequestBody RoleVO role) {

        try {
            //roleService.updateByPrimaryKeySelective(role);
            roleService.updateRoleAndRoleFunction(role);
        } catch (Exception e) {
            e.printStackTrace();
            return getResponseJson(SystemConstant.UPDATE_SUCCESS, false, null);
        }
        return getResponseJson(SystemConstant.UPDATE_SUCCESS, true, null);
    }

    /**
     * 批量删除角色
     *
     */
    @RequestMapping(value = "/batch", method = RequestMethod.DELETE)
    public ResponseJson deleteUsers(@RequestBody List<String> idList) throws Exception {
        boolean isSuccess = roleService.deleteByPrimaryKeyList(idList);
        return ServiceUtil.getResponseJson("删除成功", isSuccess, null);
    }
}
