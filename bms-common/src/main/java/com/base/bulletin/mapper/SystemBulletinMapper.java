package com.base.bulletin.mapper;

import com.base.bulletin.model.BulletinReadRecord;
import com.base.bulletin.model.SystemBulletin;
import com.base.bulletin.model.SystemBulletinSearchCondition;
import com.common.framework.base.BaseMapper;

import java.util.List;

/**
 * 系统公告Dao
 *
 *
 */
public interface SystemBulletinMapper extends BaseMapper<SystemBulletin> {

    List<SystemBulletin> selectListBySystemBulletin(SystemBulletinSearchCondition condition);

    List<BulletinReadRecord> selectBulletinAll();

    List<SystemBulletin> queryLimitThree();

}
