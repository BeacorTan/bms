package com.common.shiro;

import com.base.session.model.SystemSession;
import com.base.session.service.SessionService;
import com.common.framework.base.BaseModel;
import com.common.framework.util.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;

public class SystemSessionDAO extends CachingSessionDAO {

    private Logger LOGGER = LoggerFactory.getLogger(CachingSessionDAO.class);

    @Resource
    private SessionService sessionService;


    @Override
    protected void doUpdate(Session session) {

//        if (SessionContext.isBefore()) {
//            if (LOGGER.isInfoEnabled()) {
//                LOGGER.info("================================== SystemSessionDAO update ======================================");
//            }
//            return;
//        }
//        SessionContext.setSessionContext(session);

        if (session == null || session.getId() == null) {
            return;
        }

        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return; //如果会话过期/停止 没必要再更新了
        }

        Object loginName = session.getAttribute("loginName");
        //是否是游客,是游客,还没有登录,那就不更新
        if (loginName == null) {
            return;
        }
        SystemSession sessionModel = new SystemSession();
        sessionModel.setSessionId(session.getId().toString());
        sessionModel.setIp(session.getHost());
        sessionModel.setSessionValue(SerializableUtils.serialize(session));
        sessionModel.setUpdateDate(new Date());
        sessionModel.setId(session.getId().toString());
        sessionModel.setLoginName(loginName.toString());
        sessionModel.setLastAccessTime(session.getLastAccessTime());
        sessionService.updateByPrimaryKeySelective(sessionModel);
    }

    @Override
    protected void doDelete(Session session) {
        SystemSession systemSession = new SystemSession();
        systemSession.setId(session.getId().toString());
        systemSession.setUpdateDate(new Date());
        systemSession.setActiveFlag(BaseModel.ACTIVE_FLAG_NO);
        sessionService.updateByPrimaryKeySelective(systemSession);
    }

    /**
     * 只要第一次访问了项目就有了一次会话,就会产生一session,但此时为无效session,游客身份
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        SystemSession sessionModel = new SystemSession();
        sessionModel.setSessionId(sessionId.toString());
        sessionModel.setIp(session.getHost());
        sessionModel.setSessionValue(SerializableUtils.serialize(session));
        sessionModel.setCreateBy("sessionManager");
        sessionModel.setCreateDate(new Date());
        sessionModel.setActiveFlag(BaseModel.ACTIVE_FLAG_YES);
        sessionModel.setId(sessionId.toString());
        //游客身份,还没有登录
        sessionModel.setLoginName("guest");
        sessionService.insertSelective(sessionModel);
        super.cache(session, session.getId());
        return session.getId();
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        SystemSession systemSession = new SystemSession();
        systemSession.setSessionId(sessionId.toString());
        systemSession.setActiveFlag(BaseModel.ACTIVE_FLAG_YES);
        systemSession = sessionService.selectOne(systemSession);
        if (systemSession == null) {
            return null;
        }
        Session session = SerializableUtils.deserialize(systemSession.getSessionValue());
        super.cache(session, session.getId());
        return session;
    }

}
