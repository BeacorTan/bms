package com.base.session.mapper;

import com.common.framework.base.BaseMapper;
import com.base.session.model.SystemSession;

/**
 * ${DESCRIPTION}
 */
public interface SessionMapper extends BaseMapper<SystemSession> {

    int selectOnlineCount() throws Exception;

}
