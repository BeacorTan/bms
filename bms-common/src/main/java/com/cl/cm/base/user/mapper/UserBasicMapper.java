package com.cl.cm.base.user.mapper;


import com.cl.cm.base.user.model.UserBasic;
import com.cl.cm.base.user.model.UserBasicVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户mapper
 */
public interface UserBasicMapper extends Mapper<UserBasic> {
    /**
     * 根据登录名称查询用户实体类
     *
     * @param loginName
     * @return
     */
    UserBasic selectByLoginName(String loginName);


    int insert(UserBasic userBasic);

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


    UserBasicVO selectByID(String id);


    void deleteByIDs(List<String> ids);

    int updateUserByID(UserBasic userBasic);

}
