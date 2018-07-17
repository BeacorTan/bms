package com.base.user.controller;

import com.base.user.model.UserBasic;
import com.base.user.model.UserBasicVO;
import com.base.user.model.UserPasswordVO;
import com.base.user.service.UserBasicService;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import com.common.mapper.SysFileMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * 用户管理
 *
 * @date 2017年02月28日
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserBasicService userBasicService;

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
        return new ModelAndView("user/user_main");
    }

    /**
     * 分页查询用户集合
     *
     * @return UserSample
     */
    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    @ResponseBody
    public PagedResult<UserBasic> getUserPageList(PageBean pageBean, UserBasicVO user) throws Exception {
        return userBasicService.selectPageList(pageBean, user);
    }

    /**
     * 用户修改页面
     *
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(String id, ModelMap modelMap) {
        if (StringUtils.isNotBlank(id) && !id.startsWith(SystemConstant.ADD_VIEW_TAB_ID_PREFIX)) {
            modelMap.put("user", userBasicService.selectByPrimaryKey(id));
        }
        modelMap.put(SystemConstant.PROFILE_TAB_ID_ATTRIBUTE_NAME, id);
        return new ModelAndView("user/user_profile", modelMap);
    }

    /**
     * 用户修改
     *
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseJson editUser(@RequestBody UserBasicVO user) {
        return userBasicService.editUser(user);
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
        return ServiceUtil.getValidJson(userBasicService.isExitLoginName(loginName));
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseJson deleteUsers(@RequestBody List<String> idList) {
        return userBasicService.removeUserByKeys(idList);
    }

    @RequestMapping(value = "/personalInfo", method = RequestMethod.GET)
    public ModelAndView personalInfoView(String id, ModelMap map) {
        map.put("user", userBasicService.selectByPrimaryKey(id));
        map.put(SystemConstant.PROFILE_TAB_ID_ATTRIBUTE_NAME, id);
        return new ModelAndView("user/user_info",map);
    }

    @RequestMapping(value = "/personalInfo", method = RequestMethod.POST)
    public ResponseJson editPersonalInfo(@RequestBody UserBasic userBasic) {
        return userBasicService.editUser(userBasic);
    }

    @RequestMapping(value = "/personalInfo/updatePassword", method = RequestMethod.POST)
    public ResponseJson updatePassword(@RequestBody UserPasswordVO user) {
        return userBasicService.updatePassword(user);
    }
}
