package com.cl.common.service.impl;

import com.cl.common.framework.base.BaseMapper;
import com.cl.common.framework.base.BaseServiceImpl;
import com.cl.common.mapper.SessionMapper;
import com.cl.common.model.SystemSession;
import com.cl.common.service.SessionService;
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
