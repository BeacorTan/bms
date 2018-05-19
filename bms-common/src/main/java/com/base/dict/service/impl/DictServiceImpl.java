package com.base.dict.service.impl;

import com.base.dict.service.IDictService;
import com.base.dict.mapper.DictMapper;
import com.base.dict.model.DictVO;
import com.common.framework.base.BaseModel;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BoSongsh
 * @create 2018-03-01 17:23
 **/
@Service
public class DictServiceImpl implements IDictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<DictVO> selectByDictCode(String dictCode) {
        if ("PRODUCT_NAME".equals(dictCode)) {
            return dictMapper.getProductName();
        }
        return dictMapper.selectByDictCode(dictCode);
    }

    @Override
    public PagedResult<DictVO> selectPageList(PageBean pageBean, DictVO dict) {
        ServiceUtil.startPage(pageBean);
        List<DictVO> queryResult = dictMapper.selectByDict(dict);
        return BeanUtil.toPagedResult(queryResult);
    }


    @Override
    public int add(DictVO dict) {
        if (dict == null) {
            return 0;
        }
        ModelUtil.insertInit(dict);
        dictMapper.insert(dict);
        return 1;
    }

    @Override
    public int update(DictVO dict) {
        if (dict == null) {
            return 0;
        }
        ModelUtil.updateInit(dict);
        dictMapper.updateById(dict);
        return 1;
    }

    @Override
    public DictVO selectById(String id) {
        return dictMapper.selectByPrimaryKey(new DictVO(id, BaseModel.ACTIVE_FLAG_YES));
    }

    @Override
    public Integer delete(String id) {
        DictVO dictVO = new DictVO(id, BaseModel.ACTIVE_FLAG_NO);
        ModelUtil.updateInit(dictVO);
        return dictMapper.updateById(dictVO);
    }
}
