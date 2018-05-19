package com.cl.cm.base.dict.service.impl;

import com.cl.cm.base.dict.mapper.DictDataMapper;
import com.cl.cm.base.dict.model.DictDataVO;
import com.cl.cm.base.dict.model.DictVO;
import com.cl.cm.base.dict.service.IDictDataService;
import com.cl.common.framework.base.BaseModel;
import com.cl.common.framework.util.BeanUtil;
import com.cl.common.framework.util.ModelUtil;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
import com.cl.common.framework.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author BoSongsh
 * @create 2018-03-01 17:23
 **/
@Service
public class DictDataServiceImpl implements IDictDataService {

    @Autowired
    private DictDataMapper dictDataMapper;

    @Override
    public List<DictDataVO> selectByDictCode(String dictCode) {
        return dictDataMapper.selectByDictCode(dictCode);
    }

    @Override
    public PagedResult<DictDataVO> selectPageList(PageBean pageBean, DictDataVO dictData) {
        ServiceUtil.startPage(pageBean);
        List<DictDataVO> queryResult = dictDataMapper.selectByDict(dictData);
        return BeanUtil.toPagedResult(queryResult);
    }


    @Override
    public int add(DictDataVO dict) {
        if (dict == null) {
            return 0;
        }
        ModelUtil.insertInit(dict);
        dictDataMapper.insert(dict);
        return 1;
    }

    @Override
    public int update(DictDataVO dict) {
        if (dict == null) {
            return 0;
        }
        ModelUtil.updateInit(dict);
        dictDataMapper.updateById(dict);
        return 1;
    }

    @Override
    public DictVO selectById(String id) {
        return dictDataMapper.selectByPrimaryKey(new DictVO(id, BaseModel.ACTIVE_FLAG_YES));
    }

    @Override
    public Integer delete(String id) {
        DictDataVO dictVO = new DictDataVO(id, BaseModel.ACTIVE_FLAG_NO);
        ModelUtil.updateInit(dictVO);
        return dictDataMapper.updateById(dictVO);
    }
}
