package com.base.dict.mapper;

import com.base.dict.model.DictDataVO;
import com.common.framework.base.BaseMapper;

import java.util.List;

public interface DictDataMapper extends BaseMapper<DictDataVO> {

    List<DictDataVO> selectByDictCode(String dictCode);

    List<DictDataVO> selectByDict(DictDataVO dict);
}
