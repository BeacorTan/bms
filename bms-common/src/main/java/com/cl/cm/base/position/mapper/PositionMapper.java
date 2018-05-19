package com.cl.cm.base.position.mapper;

import com.cl.cm.base.position.model.Position;
import com.cl.common.framework.base.BaseMapper;

import java.util.List;


public interface PositionMapper extends BaseMapper<Position> {

    List<Position> selectPageList(Position position);

    Position selectById(String id);

    int updateById(Position position);

    int delete(Position position);
}
