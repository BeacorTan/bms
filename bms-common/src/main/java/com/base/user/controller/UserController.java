package com.base.user.controller;

import com.base.user.model.UpdateUserPwdVO;
import com.base.user.model.UserBasic;
import com.base.user.model.UserBasicVO;
import com.base.user.service.UserBasicService;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.HelpUtils;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import com.common.mapper.SysFileMapper;
import com.common.model.SysFile;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * 用户管理
 *
 *
 * @date 2017年02月28日
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserBasicService userBasiccService;

    @Resource
    private SysFileMapper sysMapper;

    @Value(value = "${default.photo.path}")
    private String defaultPath;

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    /**
     * 用户管理主页面
     *
     * @return
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView userMain() {
        return new ModelAndView("user/user_list");
    }

    /**
     * 分页查询用户集合
     *
     * @return UserSample
     */
    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<UserBasic> getUserPageList(PageBean pageBean, UserBasicVO user) throws Exception {
        return userBasiccService.selectPageList(pageBean, user);
    }

    /**
     * 用户profile页面【修改页面】
     *
     * @return
     */
    @RequestMapping(value = "/profile/view/{userId}", method = RequestMethod.GET)
    public ModelAndView userUpdateView(@PathVariable(value = "userId") String userId, String operator, ModelMap modelMap) throws Exception {
//        User userInfo = userService.selectByPrimaryKey(userId);
        UserBasicVO userInfo = userBasiccService.selectByPrimaryKey(userId);
        if (HelpUtils.isNotEmpty(operator) && HelpUtils.isNotEmpty(userInfo)) {
            SysFile sysFile = new SysFile();
            sysFile.setParentId(userId);//根据用户主键查询头像
            List<SysFile> sysFileList = sysMapper.selectByParentId(sysFile);
            if (HelpUtils.isNotEmpty(sysFileList)) {//不为空,说明是修改操作
                modelMap.put("filePath", "http://121.42.166.32/" + sysFileList.get(0).getFilePath());
            } else {
                modelMap.put("filePath", defaultPath);
            }
            modelMap.put("operator", operator);
            modelMap.put("userInfo", userInfo);
            return new ModelAndView("user/user_profile", modelMap);
        } else {
            modelMap.put("status", SystemConstant.ERROR_CODE_500);
            modelMap.put("error", "请填写准确的参数!");
            return new ModelAndView(SystemConstant.ERROR_PAGE, modelMap);
        }
    }

    @RequestMapping(value = "/add/view", method = RequestMethod.GET)
    public ModelAndView userAddView(String id, ModelMap modelMap) throws Exception {
        //List<Role> roles = roleService.selectAll();
        if (StringUtils.isNotBlank(id)) {
            UserBasicVO user = userBasiccService.selectByPrimaryKey(id);
            modelMap.put("user", user);
        }
        //modelMap.put("roles", roles);
        return new ModelAndView("user/user_add", modelMap);
    }


    /**
     * 增加用户操作
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseJson userAdd(@RequestBody UserBasicVO user) throws Exception {
//        userService.userAdd(user);
        userBasiccService.userAdd(user);
        return getResponseJson(SystemConstant.ADD_SUCCESS, true, null);
    }

    /**
     * 修改用户操作
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseJson userUpdate(@RequestBody UserBasicVO user) throws Exception {
        if (HelpUtils.isNotEmpty(user.getPassword())) {//修改了密码
            if (user.getLoginName().equalsIgnoreCase("admin")) {
                return ServiceUtil.getResponseJson("请不要修改管理员的密码", false, null);
            }
        }
        try {
            userBasiccService.updateUser(user);
        } catch (Exception e) {
            LOGGER.error("修改用户异常：{}", e);
            return getResponseJson("修改失败", false, null);
        }
        return getResponseJson(SystemConstant.UPDATE_SUCCESS, true, null);
    }


    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public ResponseJson updatePwd(UpdateUserPwdVO user) {
        String pwd = user.getCurrentPwd();
        if (StringUtils.isEmpty(pwd)) {
            LOGGER.error("非超级管理员不能修改");
        }

        String confirmPwd = user.getConfirmPwd();
        // 确认新密码
        String newPwd = user.getNewPwd();
        if (StringUtils.isEmpty(confirmPwd) || StringUtils.isEmpty(newPwd) || (!newPwd.equals(confirmPwd))) {
            LOGGER.error("新密码和确认新密码不一致！");
            return getResponseJson("修改失败", false, "新密码和确认新密码不一致！");
        }
        UserBasicVO userBasic = new UserBasicVO();
        userBasic.setId(user.getId());
        userBasic.setPassword(user.getConfirmPwd());
        userBasic.setLoginName(user.getLoginName());
        try {
            userBasiccService.updateUser(userBasic);
        } catch (Exception e) {
            LOGGER.error("修改密码异常：{}", e);
            return getResponseJson("修改失败", false, null);
        }
        return getResponseJson(SystemConstant.UPDATE_SUCCESS, true, null);
    }


    /**
     * 验证登录账号是否唯一
     *
     * @param loginName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uniqueness/loginName")
    public Object checkLoginName(String loginName) {
        try {
            return ServiceUtil.getValidJson(userBasiccService.isExitLoginName(loginName));
        } catch (Exception e) {
            return ServiceUtil.getValidJson(false);
        }
    }

    /**
     * 批量删除用户
     *
     * @param idList 用户集合
     */
    @RequestMapping(value = "/batch", method = RequestMethod.DELETE)
    public ResponseJson deleteUsers(@RequestBody List<String> idList) throws Exception {
//        boolean isSuccess = userService.deleteByPrimaryKeyList(idList);
        try {
            userBasiccService.deleteByIDs(idList);
        } catch (Exception e) {
            return getResponseJson("删除失败", false, null);
        }
        return getResponseJson("删除成功", true, null);
    }
}
