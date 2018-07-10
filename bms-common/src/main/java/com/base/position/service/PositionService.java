package com.base.position.service;


import com.base.position.model.Position;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

import java.util.List;

/**
 * 角色接口
 */
public interface PositionService {

    ResponseJson editPosition(Position pojo);

    PagedResult<Position> selectPageList(PageBean pageBean, Position role);

    Position queryById(String id);

    ResponseJson updateActiveFlagByPrimaryKeyList(List<String> keys);

}
