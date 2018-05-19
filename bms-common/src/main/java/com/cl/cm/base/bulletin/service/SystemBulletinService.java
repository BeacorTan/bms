package com.cl.cm.base.bulletin.service;

import com.cl.cm.base.bulletin.model.BulletinReadRecord;
import com.cl.cm.base.bulletin.model.SystemBulletin;
import com.cl.common.framework.base.BaseService;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
import com.cl.common.framework.util.ResponseJson;

import java.util.List;

/**
 * 系统公告Service
 * @author XingyuLi
 *
 */
public interface SystemBulletinService extends BaseService<SystemBulletin>
{
	PagedResult<SystemBulletin> selectSystemBulletinPageList(PageBean pageBean, SystemBulletin systemBulletin);

	PagedResult<BulletinReadRecord>  readStatusByLoginName(PageBean pageBean);

	List<SystemBulletin> queryLimitThree();

	ResponseJson systemBulletinEditService(SystemBulletin systemBulletin);
	
	
	SystemBulletin selectBulletinByPrimaryKey(String id) throws Exception;

	void saveReadLog(String bulletinId);


}
