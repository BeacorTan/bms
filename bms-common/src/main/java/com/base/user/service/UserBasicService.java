package com.base.user.service;


import com.base.user.model.UpdateUserPwdVO;
import com.base.user.model.UserBasic;
import com.base.user.model.UserBasicVO;
import com.base.user.model.UserVO;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

import java.util.List;

/**
 * 用户接口
 *
 *
 * @date 2016年08月30日
 */
public interface UserBasicService {

    ResponseJson editUser(UserBasicVO user);

    /**
     * 根据用户登录名查找用户
     *
     * @param loginName
     * @return
     * @throws Exception
     */
    UserBasic selectByLoginName(String loginName);


    UserVO selectByPrimaryKey(String id);

    /**
     * 根据查询条件分页查询用户信息
     *
     * @param user
     * @return
     * @throws Exception
     */
    PagedResult<UserBasic> selectPageList(PageBean pageBean, UserBasicVO user);

    /**
     * 验证登录账号是否存在
     *
     * @param loginName
     * @return
     * @throws Exception
     */
    boolean isExitLoginName(String loginName);


    ResponseJson removeUserByKeys(List<String> ids);

    ResponseJson updatePassword(UpdateUserPwdVO user);
}
