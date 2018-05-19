package com.cl.cm.base.bulletin.mapper;

import com.cl.cm.base.bulletin.model.ReadBulletinLog;
import com.cl.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统公告Dao
 *
 * @author XingyuLi
 */
public interface ReadBulletinLogMapper extends BaseMapper<ReadBulletinLog> {

    int countByLoginName(@Param("loginName") String loginName,@Param("bulletinId") String bulletinId);

}
