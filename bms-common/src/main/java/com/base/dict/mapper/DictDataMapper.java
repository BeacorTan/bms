package com.base.dict.mapper;

import com.base.dict.model.DictDataVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DictDataMapper extends Mapper<DictDataVO> {

    List<DictDataVO> selectByDictCode(String dictCode);

    List<DictDataVO> selectByDict(DictDataVO dict);

    int updateById(DictDataVO dict);

}
