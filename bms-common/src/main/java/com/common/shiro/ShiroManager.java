package com.common.shiro;

import com.base.user.model.UserBasic;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro api封装类
 *
 *
 * @date 2017年02月27日
 */
public class ShiroManager {
    /**
     * 获取Shiro Subject
     * @return
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    /**
     * 获取Shiro
     * @return
     */
    public static Session getSession(){
        return getSubject().getSession();
    }

    /**
     * 获取系统登录账号
     * @return
     */
    public static String getLoginName(){
        return ((UserBasic)getSubject().getPrincipal()).getLoginName();
    }

    /**
     * 获取系统登录session
     * @return
     */
    public static UserBasic getUserInfo(){
        return (UserBasic) getSubject().getPrincipal();
    }


}
