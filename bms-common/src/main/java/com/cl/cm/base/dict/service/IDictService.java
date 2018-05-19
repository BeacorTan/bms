package com.cl.cm.base.dict.service;

import com.cl.cm.base.dict.model.DictVO;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;

import java.util.List;

/**
 * @author BoSongsh
 * @create 2018-03-01 17:22
 **/
public interface IDictService {

    List<DictVO> selectByDictCode(String dictCode);


    PagedResult<DictVO> selectPageList(PageBean pageBean, DictVO dict);

    int add(DictVO dict);

    int update(DictVO dict);

    DictVO selectById(String id);


    Integer delete(String id);
}
