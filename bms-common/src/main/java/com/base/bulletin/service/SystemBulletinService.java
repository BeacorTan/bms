package com.base.bulletin.service;

import com.base.bulletin.model.BulletinReadRecord;
import com.base.bulletin.model.SystemBulletin;
import com.base.bulletin.model.SystemBulletinSearchCondition;
import com.common.framework.base.BaseService;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;

import java.util.List;

/**
 * 系统公告Service
 *
 *
 */
public interface SystemBulletinService extends BaseService<SystemBulletin>
{
	PagedResult<SystemBulletin> selectSystemBulletinPageList(PageBean pageBean, SystemBulletinSearchCondition condition);

	PagedResult<BulletinReadRecord>  readStatusByLoginName(PageBean pageBean);

	List<SystemBulletin> queryLimitThree();

	ResponseJson editBulletin(SystemBulletin systemBulletin);

	ResponseJson removeByIds(List<String> ids);

	void saveReadLog(String bulletinId);
}
