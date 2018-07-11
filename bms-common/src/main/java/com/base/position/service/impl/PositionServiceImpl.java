package com.base.position.service.impl;

import com.base.position.mapper.PositionMapper;
import com.base.position.model.Position;
import com.base.position.service.PositionService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 职位
 */
@Service
public class PositionServiceImpl extends BaseServiceImpl<Position> implements PositionService {

//    private Logger LOGGER = LoggerFactory.getLogger(PositionServiceImpl.class);

    @Resource
    private PositionMapper positionMapper;

    @Override
    public BaseMapper getMapper() {
        return positionMapper;
    }

    @Transactional
    @Override
    public ResponseJson editPosition(Position position) {
        if (position == null) {
            return ServiceUtil.getResponseJson("传入职位信息为空", SystemConstant.RESPONSE_ERROR);
        }
        if(StringUtils.isNotBlank(position.getId())){
            ModelUtil.updateInit(position);
            this.updateByPrimaryKeySelective(position);
        }else{
            ModelUtil.insertInit(position);
            position.setPositionCode("POST" + System.currentTimeMillis());
            this.insertSelective(position);
        }
        return ServiceUtil.getResponseJson("职位编辑成功",SystemConstant.RESPONSE_SUCCESS);
    }

    @Override
    public PagedResult<Position> selectPageList(PageBean pageBean, Position position) {
        ServiceUtil.startPage(pageBean);
        List<Position> queryResult = positionMapper.selectPageList(position);
        return BeanUtil.toPagedResult(queryResult);
    }

    @Override
    public Position queryById(String id) {
        return positionMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResponseJson updateActiveFlagByPrimaryKeyList(List<String> keys) {
        Position position=new Position();
        ModelUtil.deleteInit(position);
        if(!this.updateActiveFlagByPrimaryKeyList(keys,position)){
            return ServiceUtil.getResponseJson("传入数据为空",SystemConstant.RESPONSE_ERROR);
        }
        return ServiceUtil.getResponseJson("删除成功",SystemConstant.RESPONSE_SUCCESS);
    }
}
