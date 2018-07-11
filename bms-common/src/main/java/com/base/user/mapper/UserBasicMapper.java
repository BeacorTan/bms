package com.base.user.mapper;


import com.base.user.model.UserBasic;
import com.base.user.model.UserVO;
import com.common.framework.base.BaseMapper;

import java.util.List;

/**
 * 用户mapper
 */
public interface UserBasicMapper extends BaseMapper<UserBasic> {
    /**
     * 根据登录名称查询用户实体类
     *
     * @param loginName
     * @return
     */
    UserBasic selectByLoginName(String loginName);


    /**
     * 根据条件查询用户列表
     *
     * @param user
     * @return
     */
    List<UserBasic> selectListByUser(UserBasic user);

    /**
     * 登录名的唯一性判断
     *
     * @param loginName
     * @return
     */
    int isExitLoginName(String loginName);

    UserVO selectById(String id);
}
