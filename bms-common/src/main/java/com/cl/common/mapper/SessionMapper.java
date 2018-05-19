package com.cl.common.mapper;

import com.cl.common.framework.base.BaseMapper;
import com.cl.common.model.SystemSession;

/**
 * ${DESCRIPTION}
 */
public interface SessionMapper extends BaseMapper<SystemSession> {

    int selectOnlineCount() throws Exception;

}
