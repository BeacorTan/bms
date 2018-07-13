package com.base.dict.service.impl;

import com.base.dict.mapper.DictMapper;
import com.base.dict.model.DictVO;
import com.base.dict.service.IDictService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.constant.SystemConstant;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DictServiceImpl extends BaseServiceImpl<DictVO> implements IDictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<DictVO> selectByDictCode(String dictCode) {
        return dictMapper.selectByDictCode(dictCode);
    }

    @Override
    public PagedResult<DictVO> selectPageList(PageBean pageBean, DictVO dict) {
        ServiceUtil.startPage(pageBean);
        List<DictVO> queryResult = dictMapper.selectByDict(dict);
        return BeanUtil.toPagedResult(queryResult);
    }

    @Transactional
    @Override
    public ResponseJson editDict(DictVO dict) {

        if (dict == null) {
            return ServiceUtil.getResponseJson("输入数据为空", SystemConstant.RESPONSE_ERROR);
        }

        String id=dict.getId();
        if(StringUtils.isEmpty(id)){
            ModelUtil.insertInit(dict);
            dictMapper.insertSelective(dict);
        }else{
            ModelUtil.updateInit(dict);
            dictMapper.updateByPrimaryKeySelective(dict);
        }
        return ServiceUtil.getResponseJson(SystemConstant.UPDATE_SUCCESS, SystemConstant.RESPONSE_SUCCESS);
    }


    @Override
    public DictVO selectById(String id) {
        return dictMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public ResponseJson removeByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ServiceUtil.getResponseJson("删除失败，输入数据为空", SystemConstant.RESPONSE_ERROR);
        }
        DictVO dictVO=new DictVO();
        ModelUtil.deleteInit(dictVO);
        this.updateActiveFlagByPrimaryKeyList(ids,dictVO);
        return ServiceUtil.getResponseJson("删除成功", SystemConstant.RESPONSE_SUCCESS);
    }


    @Override
    public BaseMapper getMapper() {
        return dictMapper;
    }
}
