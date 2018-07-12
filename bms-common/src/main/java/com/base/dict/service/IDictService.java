package com.base.dict.service;

import com.base.dict.model.DictVO;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

import java.util.List;

public interface IDictService {

    List<DictVO> selectByDictCode(String dictCode);

    PagedResult<DictVO> selectPageList(PageBean pageBean, DictVO dict);

    ResponseJson editDict(DictVO dict);

    DictVO selectById(String id);

    ResponseJson removeByIds(List<String> ids);
}
