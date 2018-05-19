package com.cl.cm.base.user.service;


import com.cl.cm.base.user.model.UserBasic;
import com.cl.cm.base.user.model.UserBasicVO;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;

import java.util.List;

/**
 * 用户接口
 *
 *
 * @date 2016年08月30日
 */
public interface UserBasicService {


    void userAdd(UserBasicVO userVO);


    void updateUser(UserBasicVO userVO);

    /**
     * 根据用户登录名查找用户
     *
     * @param loginName
     * @return
     * @throws Exception
     */
    UserBasic selectByLoginName(String loginName);


    UserBasicVO selectByPrimaryKey(String id);

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


    void deleteByIDs(List<String> ids);
}
