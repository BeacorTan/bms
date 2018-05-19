package com.base.dict.mapper;

import com.base.dict.model.DictVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DictMapper extends Mapper<DictVO> {

    List<DictVO> selectByDictCode(String dictCode);

    List<DictVO> getProductName();

    List<DictVO> selectByDict(DictVO dict);

    int updateById(DictVO dict);

}
