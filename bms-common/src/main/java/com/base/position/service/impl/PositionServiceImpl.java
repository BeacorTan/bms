package com.base.position.service.impl;

import com.base.position.mapper.PositionMapper;
import com.base.position.model.Position;
import com.base.position.service.PositionService;
import com.common.framework.base.BaseModel;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 职位
 */
@Service
public class PositionServiceImpl implements PositionService {

    private Logger LOGGER = LoggerFactory.getLogger(PositionServiceImpl.class);

    @Resource
    private PositionMapper positionMapper;


    @Override
    public int insert(Position position) {
        if (position == null) {
            LOGGER.error("新增职位对象为空!");
            return 0;
        }
        ModelUtil.insertInit(position);
        position.setPositionCode("POST" + System.currentTimeMillis());
        return positionMapper.insert(position);
    }

    @Override
    public int update(Position position) {
        if (position == null) {
            LOGGER.error("修改职位对象为空!");
            return 0;
        }
        ModelUtil.updateInit(position);
        return positionMapper.updateById(position);
    }

    @Override
    public int delete(Position position) {
        ModelUtil.updateInit(position);
        position.setActiveFlag(BaseModel.ACTIVE_FLAG_NO);
        return positionMapper.delete(position);
    }

    @Override
    public PagedResult<Position> selectPageList(PageBean pageBean, Position position) {
        ServiceUtil.startPage(pageBean);
        List<Position> queryResult = positionMapper.selectPageList(position);
        return BeanUtil.toPagedResult(queryResult);
    }



    @Override
    public Position queryById(String id) {
        return positionMapper.selectById(id);
    }
}
