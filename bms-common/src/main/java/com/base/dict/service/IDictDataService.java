package com.base.dict.service;

import com.base.dict.model.DictDataVO;
import com.base.dict.model.DictVO;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;

import java.util.List;

/**
 * @author BoSongsh
 * @create 2018-03-01 17:22
 **/
public interface IDictDataService {

    List<DictDataVO> selectByDictCode(String dictCode);


    PagedResult<DictDataVO> selectPageList(PageBean pageBean, DictDataVO dictData);

    int add(DictDataVO dict);

    int update(DictDataVO dict);

    DictVO selectById(String id);


    Integer delete(String id);
}
