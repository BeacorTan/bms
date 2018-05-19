package com.cl.common.mapper;

import com.cl.common.model.SysFile;
import com.cl.common.framework.base.BaseMapper;

import java.util.List;

/**
 * 文件
 *
 *
 * @date 2017年03月31日
 */

public interface SysFileMapper extends BaseMapper<SysFile>{

    List<SysFile> selectByParentId(SysFile sysFile);
}
