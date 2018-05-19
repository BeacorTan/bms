package com.cl.common.service.impl;

import com.cl.common.mapper.SysFileMapper;
import com.cl.common.model.SysFile;
import com.cl.common.service.SysFileService;
import com.cl.common.framework.base.BaseMapper;
import com.cl.common.framework.base.BaseServiceImpl;
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
