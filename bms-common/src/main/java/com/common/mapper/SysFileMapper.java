package com.common.mapper;

import com.common.model.SysFile;
import com.common.framework.base.BaseMapper;

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
