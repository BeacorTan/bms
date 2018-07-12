package com.base.dict.mapper;

import com.base.dict.model.DictVO;
import com.common.framework.base.BaseMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DictMapper extends BaseMapper<DictVO> {

    List<DictVO> selectByDictCode(String dictCode);

    List<DictVO> selectByDict(DictVO dict);

}
