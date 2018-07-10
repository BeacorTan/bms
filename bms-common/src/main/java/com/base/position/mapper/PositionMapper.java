package com.base.position.mapper;

import com.base.position.model.Position;
import com.common.framework.base.BaseMapper;

import java.util.List;


public interface PositionMapper extends BaseMapper<Position> {

    List<Position> selectPageList(Position position);

}
