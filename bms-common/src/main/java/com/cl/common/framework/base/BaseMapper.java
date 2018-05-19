package com.cl.common.framework.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 基础Mapper
 *
 *
 * @date 2017年02月22日
 */

public interface BaseMapper <T> extends Mapper<T>,MySqlMapper<T> {
}
