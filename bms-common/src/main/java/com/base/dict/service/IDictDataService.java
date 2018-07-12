package com.base.dict.service;

import com.base.dict.model.DictDataVO;
import com.base.dict.model.DictVO;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

import java.util.List;

public interface IDictDataService {

    List<DictDataVO> selectByDictCode(String dictCode);

    PagedResult<DictDataVO> selectPageList(PageBean pageBean, DictDataVO dictData);

    DictVO selectById(String id);

    ResponseJson editDictData(DictDataVO dictVO);

    ResponseJson removeByIds(List<String> ids);
}
