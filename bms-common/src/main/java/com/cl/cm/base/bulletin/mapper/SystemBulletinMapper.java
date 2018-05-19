package com.cl.cm.base.bulletin.mapper;

import com.cl.cm.base.bulletin.model.BulletinReadRecord;
import com.cl.cm.base.bulletin.model.SystemBulletin;
import com.cl.common.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统公告Dao
 *
 * @author XingyuLi
 */
public interface SystemBulletinMapper extends BaseMapper<SystemBulletin> {
    List<SystemBulletin> selectListBySystemBulletin(SystemBulletin systemBulletin);

    List<BulletinReadRecord> selectBulletinAll();


    List<SystemBulletin> queryLimitThree();


    SystemBulletin selectBulletinByPrimaryKey(@Param("id") String id);
}
