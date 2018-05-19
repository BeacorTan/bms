package com.common.service;

import com.common.framework.base.BaseService;
import com.common.model.SystemSession;


public interface SessionService extends BaseService<SystemSession> {
    int selectOnlineCount() throws Exception;
}
