package com.base.session.service.impl;

import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.base.session.mapper.SessionMapper;
import com.base.session.model.SystemSession;
import com.base.session.service.SessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * session实现类
 *
 */
@Service
public class SessionServiceImpl extends BaseServiceImpl<SystemSession> implements SessionService {

    @Resource
    private SessionMapper sessionMapper;

    @Override
    public BaseMapper getMapper() {
        return sessionMapper;
    }

    @Override
    public int selectOnlineCount() throws Exception {
        return sessionMapper.selectOnlineCount();
    }
}
