package com.common.mapper;

import com.common.framework.base.BaseMapper;
import com.common.model.SystemSession;

/**
 * ${DESCRIPTION}
 */
public interface SessionMapper extends BaseMapper<SystemSession> {

    int selectOnlineCount() throws Exception;

}
