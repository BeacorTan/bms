package com.base.bulletin.service.impl;

import com.base.bulletin.mapper.ReadBulletinLogMapper;
import com.base.bulletin.model.BulletinReadRecord;
import com.base.bulletin.model.ReadBulletinLog;
import com.base.bulletin.model.SystemBulletin;
import com.base.bulletin.mapper.SystemBulletinMapper;
import com.base.bulletin.service.SystemBulletinService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.ModelUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import com.common.shiro.ShiroManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告Service
 *
 *
 */
@Service
public class SystemBulletinServiceImpl extends BaseServiceImpl<SystemBulletin> implements SystemBulletinService {

    @Autowired
    private SystemBulletinMapper systemBulletinMapper;

    @Autowired
    private ReadBulletinLogMapper readBulletinLogMapper;

    private final String NO_READ = "未读";
    private final String YES_READ = "已读";

    public BaseMapper getMapper() {
        return this.systemBulletinMapper;
    }


    /**
     * 查询列表页面
     */
    public PagedResult<SystemBulletin> selectSystemBulletinPageList(
            PageBean pageBean, SystemBulletin systemBulletin) {
        ServiceUtil.startPage(pageBean);
        return BeanUtil.toPagedResult(systemBulletinMapper.selectListBySystemBulletin(systemBulletin));
    }

    @Override
    public PagedResult<BulletinReadRecord> readStatusByLoginName(PageBean pageBean) {
        ServiceUtil.startPage(pageBean);
        List<BulletinReadRecord> readRecordList = systemBulletinMapper.selectBulletinAll();
        String loginName = ShiroManager.getLoginName();

        int readStatus = 0;

        if (CollectionUtils.isNotEmpty(readRecordList)) {
            for (BulletinReadRecord r : readRecordList) {
                readStatus = readBulletinLogMapper.countByLoginName(loginName, r.getId());
                if (readStatus > 0) {
                    r.setReadStatus(this.YES_READ);
                } else {
                    r.setReadStatus(this.NO_READ);
                }
            }
        }
        return BeanUtil.toPagedResult(readRecordList);
    }

    @Override
    public List<SystemBulletin> queryLimitThree() {
        return systemBulletinMapper.queryLimitThree();
    }

    public ResponseJson systemBulletinEditService(SystemBulletin systemBulletin) {

        if (systemBulletin == null) {
            return ServiceUtil.getResponseJson("编辑失败", false);
        }

        boolean flag = true;
        String msg = "编辑成功";
        String id = systemBulletin.getId();

        try {
            if (StringUtils.isEmpty(id)) {
                ModelUtil.insertInit(systemBulletin);
                systemBulletinMapper.insertSelective(systemBulletin);
            } else {
                ModelUtil.updateInit(systemBulletin);
                systemBulletinMapper.updateByPrimaryKeySelective(systemBulletin);
            }
        } catch (Exception e) {
            flag = false;
            msg = e.getMessage();
        }
        return ServiceUtil.getResponseJson(msg, flag);

    }

    public SystemBulletin selectBulletinByPrimaryKey(String id)
            throws Exception {
        SystemBulletin pojo = systemBulletinMapper.selectBulletinByPrimaryKey(id);
        return pojo;
    }

    @Override
    public void saveReadLog(String bulletinId) {
        ReadBulletinLog readBulletinLog = new ReadBulletinLog(ShiroManager.getLoginName(), bulletinId);
        ModelUtil.insertInit(readBulletinLog);
        readBulletinLogMapper.insertSelective(readBulletinLog);
    }

}
