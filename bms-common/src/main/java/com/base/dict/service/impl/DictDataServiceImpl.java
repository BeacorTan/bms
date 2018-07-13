package com.base.dict.service.impl;

import com.base.dict.mapper.DictDataMapper;
import com.base.dict.model.DictDataVO;
import com.base.dict.model.DictVO;
import com.base.dict.service.IDictDataService;
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

import java.util.List;

@Service
public class DictDataServiceImpl extends BaseServiceImpl<DictDataVO> implements IDictDataService {

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
    public DictVO selectById(String id) {
        return dictDataMapper.selectByPrimaryKey(id);
    }


    @Override
    public ResponseJson editDictData(DictDataVO dictVO) {
        if (dictVO == null) {
            return ServiceUtil.getResponseJson("输入数据为空", SystemConstant.RESPONSE_ERROR);
        }
        String id=dictVO.getId();

        if(StringUtils.isBlank(id)){
            ModelUtil.insertInit(dictVO);
            dictDataMapper.insertSelective(dictVO);
        }else{
            ModelUtil.updateInit(dictVO);
            dictDataMapper.updateByPrimaryKeySelective(dictVO);
        }
        return ServiceUtil.getResponseJson(SystemConstant.UPDATE_SUCCESS, SystemConstant.RESPONSE_SUCCESS);
    }

    @Override
    public ResponseJson removeByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return ServiceUtil.getResponseJson("删除失败，输入数据为空", SystemConstant.RESPONSE_ERROR);
        }
        DictDataVO dictVO=new DictDataVO();
        ModelUtil.deleteInit(dictVO);
        this.updateActiveFlagByPrimaryKeyList(ids,dictVO);
        return ServiceUtil.getResponseJson("删除成功", SystemConstant.RESPONSE_SUCCESS);
    }

    @Override
    public BaseMapper getMapper() {
        return this.dictDataMapper;
    }
}
