package com.base.session.service;

import com.common.framework.base.BaseService;
import com.base.session.model.SystemSession;


public interface SessionService extends BaseService<SystemSession> {

    int selectOnlineCount();

    SystemSession selectOne(SystemSession session);

}
