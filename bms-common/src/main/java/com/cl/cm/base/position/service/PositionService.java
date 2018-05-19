package com.cl.cm.base.position.service;


import com.cl.cm.base.position.model.Position;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;

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
