package com.base.position.service;


import com.base.position.model.Position;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;

/**
 * 角色接口
 */
public interface PositionService {

    int insert(Position pojo);

    int update(Position pojo);

    int delete(Position pojo);

    PagedResult<Position> selectPageList(PageBean pageBean, Position role);

    Position queryById(String id);


}
