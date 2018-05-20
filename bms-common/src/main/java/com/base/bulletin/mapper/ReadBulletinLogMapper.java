package com.base.bulletin.mapper;

import com.base.bulletin.model.ReadBulletinLog;
import com.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统公告Dao
 *
 *
 */
public interface ReadBulletinLogMapper extends BaseMapper<ReadBulletinLog> {

    int countByLoginName(@Param("loginName") String loginName,@Param("bulletinId") String bulletinId);

}
