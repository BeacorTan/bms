package com.base.quarz.service.impl;

import com.common.mapper.SysFileMapper;
import com.common.model.SysFile;
import com.common.service.SysFileService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文件接口实现类
 *
 *
 * @date 2017年03月31日
 */
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFile> implements SysFileService {
    @Resource
    private SysFileMapper sysMapper;
    @Override
    public BaseMapper getMapper() {
        return sysMapper;
    }


}
