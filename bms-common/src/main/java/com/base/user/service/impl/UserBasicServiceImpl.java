package com.base.user.service.impl;

import com.base.user.mapper.UserBasicMapper;
import com.base.user.mapper.UserRoleMapper;
import com.base.user.model.UpdateUserPwdVO;
import com.base.user.model.UserBasic;
import com.base.user.model.UserBasicVO;
import com.base.user.model.UserRoleMap;
import com.base.user.model.UserVO;
import com.base.user.service.UserBasicService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseModel;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import com.common.shiro.EncryptPwd;
import com.common.shiro.ShiroManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.common.framework.util.ServiceUtil.getResponseJson;


@Service(value = "userBasicService")
public class UserBasicServiceImpl extends BaseServiceImpl<UserBasic> implements UserBasicService {

    @Resource
    private UserBasicMapper userBasicMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseJson editUser(UserBasicVO user) {
        if (user == null) {
            return ServiceUtil.getResponseJson("传入参数为空", SystemConstant.RESPONSE_ERROR);
        }
        String id = user.getId();
        if (StringUtils.isNotBlank(id)) {
            this.updateUser(user);
        } else {
            this.addUser(user);
        }
        return ServiceUtil.getResponseJson(SystemConstant.UPDATE_SUCCESS, SystemConstant.RESPONSE_SUCCESS);
    }

    /**
     * 新增用户
     *
     * @param
     */
    private void addUser(UserBasicVO userVO) {
        // 新增默认密码为123456
        userVO.setPassword("123456");
        EncryptPwd.encryptPassword(userVO);
        ModelUtil.insertInit(userVO);
        userBasicMapper.insertSelective(userVO);
        List<String> roleCodes = userVO.getRoleCodes();
        if (CollectionUtils.isEmpty(roleCodes)) {
            return;
        }

        String loginName = userVO.getLoginName();
        UserRoleMap userRoleMap = new UserRoleMap(loginName);
        for (String roleCode : roleCodes) {
            if (StringUtils.isBlank(roleCode)) {
                continue;
            }
            userRoleMap.setRoleCode(roleCode);
            ModelUtil.insertInit(userRoleMap);
            userRoleMapper.insertSelective(userRoleMap);
        }
    }


    /**
     * 修改用户
     *
     * @param user
     */
    private void updateUser(UserBasicVO user) {

        userBasicMapper.updateByPrimaryKeySelective(user);
        List<String> roleCodes = user.getRoleCodes();
        if (CollectionUtils.isEmpty(roleCodes)) {
            return;
        }
        String loginName = user.getLoginName();
        UserRoleMap userRoleMap = new UserRoleMap(loginName);
        ModelUtil.deleteInit(userRoleMap);
        userRoleMapper.updateActiveFlagByRecord(userRoleMap);

        for (String roleCode : roleCodes) {
            userRoleMap.setRoleCode(roleCode);
            ModelUtil.insertInit(userRoleMap);
            userRoleMapper.insertSelective(userRoleMap);
        }
    }

    @Override
    public UserBasic selectByLoginName(String loginName) {
        return userBasicMapper.selectByLoginName(loginName);
    }

    @Override
    public UserVO selectByPrimaryKey(String id) {
        return userBasicMapper.selectById(id);
    }

    @Override
    public PagedResult<UserBasic> selectPageList(PageBean pageBean, UserBasicVO user) {
        ServiceUtil.startPage(pageBean);
        return BeanUtil.toPagedResult(userBasicMapper.selectListByUser(user));
    }


    @Override
    public boolean isExitLoginName(String loginName) {
        int result = userBasicMapper.isExitLoginName(loginName);
        if (result == 0) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public ResponseJson removeUserByKeys(List<String> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return ServiceUtil.getResponseJson("传入参数为空", SystemConstant.RESPONSE_ERROR);
        }

        UserBasic user = new UserBasic();
        ModelUtil.deleteInit(user);

        UserRoleMap userRoleMap = new UserRoleMap();
        ModelUtil.deleteInit(userRoleMap);
        this.updateActiveFlagByPrimaryKeyList(ids, user);

        Map<String,Object> delMap=new HashMap<String,Object>(2);
        delMap.put("updateBy", ShiroManager.getLoginName());
        delMap.put("updateDate",new Date());
        delMap.put("activeFlag", BaseModel.ACTIVE_FLAG_NO);
        delMap.put("ids",ids);
        userRoleMapper.updateActiveFlagByUserIds(delMap);

        return ServiceUtil.getResponseJson("删除成功", SystemConstant.RESPONSE_SUCCESS);
    }


    @Override
    public ResponseJson updatePassword(UpdateUserPwdVO user) {

        String pwd = user.getCurrentPwd();


        String confirmPwd = user.getConfirmPwd();
        // 确认新密码
        String newPwd = user.getNewPwd();

        if (StringUtils.isEmpty(confirmPwd) || StringUtils.isEmpty(newPwd) || (!newPwd.equals(confirmPwd))) {
            return getResponseJson("修改失败！新密码和确认密码不一致", SystemConstant.RESPONSE_ERROR);
        }
        user.setPassword(newPwd);
        EncryptPwd.encryptPassword(user);
        ModelUtil.updateInit(user);
        userBasicMapper.updateByPrimaryKeySelective(user);
        return getResponseJson("密码修改成功", SystemConstant.RESPONSE_SUCCESS);
    }

    @Override
    public BaseMapper getMapper() {
        return this.userBasicMapper;
    }
}
