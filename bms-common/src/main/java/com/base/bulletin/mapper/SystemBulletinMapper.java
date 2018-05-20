package com.base.bulletin.mapper;

import com.base.bulletin.model.BulletinReadRecord;
import com.base.bulletin.model.SystemBulletin;
import com.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统公告Dao
 *
 *
 */
public interface SystemBulletinMapper extends BaseMapper<SystemBulletin> {
    List<SystemBulletin> selectListBySystemBulletin(SystemBulletin systemBulletin);

    List<BulletinReadRecord> selectBulletinAll();


    List<SystemBulletin> queryLimitThree();


    SystemBulletin selectBulletinByPrimaryKey(@Param("id") String id);
}
