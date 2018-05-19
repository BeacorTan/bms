package com.common.shiro;

import com.common.framework.util.SerializableUtils;
import com.common.model.SystemSession;
import com.common.service.SessionService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SystemSessionDAO extends CachingSessionDAO {

    @Resource
    private SessionService sessionService;

    @Override
    protected void doUpdate(Session session) {
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return; //如果会话过期/停止 没必要再更新了
        }
        boolean isGuest = session.getAttribute("loginName") == null;//是否是游客
        if(isGuest){//是游客,还没有登录,那就不更新
            return;
        }
        SystemSession sessionModel = new SystemSession();
        sessionModel.setSessionId(session.getId().toString());
        sessionModel.setIp(session.getHost());
        sessionModel.setSessionValue(SerializableUtils.serialize(session));
        sessionModel.setUpdateDate(new Date());
        sessionModel.setId(session.getId().toString());
        sessionModel.setLoginName(session.getAttribute("loginName").toString());
        sessionModel.setLastAccessTime(session.getLastAccessTime());
        try {
            sessionService.updateByPrimaryKeySelective(sessionModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(Session session) {
        try {
            sessionService.deleteByPrimaryKey(session.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Serializable doCreate(Session session) {//只要第一次访问了项目就有了一次会话,就会产生一session,但此时为无效session,游客身份
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        SystemSession sessionModel = new SystemSession();
        sessionModel.setSessionId(sessionId.toString());
        sessionModel.setIp(session.getHost());
        sessionModel.setSessionValue(SerializableUtils.serialize(session));
        sessionModel.setCreateBy("sessionManager");
        sessionModel.setCreateDate(new Date());
        sessionModel.setActiveFlag(1);
        sessionModel.setId(sessionId.toString());
        sessionModel.setLoginName("guest");//游客身份,还没有登录
        try {
            sessionService.insertSelective(sessionModel);
            return session.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        SystemSession session = new SystemSession();
        session.setSessionId(sessionId.toString());
        try {
            List<SystemSession> sessionList = sessionService.select(session);
            if(sessionList.size() == 0) return null;
            return SerializableUtils.deserialize(sessionList.get(0).getSessionValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
