package com.cl.common.service;

import com.cl.common.framework.base.BaseService;
import com.cl.common.model.SystemSession;


public interface SessionService extends BaseService<SystemSession> {
    int selectOnlineCount() throws Exception;
}
