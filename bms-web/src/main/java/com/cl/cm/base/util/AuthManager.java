package com.cl.cm.base.util;

import com.cl.cm.base.role.model.RoleData;
import com.cl.cm.base.role.service.RoleDataService;
import com.cl.common.framework.util.BeanUtil;
import com.cl.common.model.AuthBase;
import com.cl.common.shiro.ShiroManager;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * 权限utils
 *
 * @author BoSongsh
 * @create 2018-04-28 10:28
 **/
@Component
public class AuthManager {

    @Autowired
    private RoleDataService roleDataService;

    public void setAuth(AuthBase authBase) throws NoSuchFieldException, IllegalAccessException {
        String loginName = ShiroManager.getLoginName();
        List<RoleData> roleDataList = roleDataService.selectByLoginName(loginName);
        if (CollectionUtils.isNotEmpty(roleDataList)) {
            Iterator<RoleData> roleDataIterator = roleDataList.iterator();
            RoleData roleData = null;
            while (roleDataIterator.hasNext()) {
                roleData = roleDataIterator.next();
                BeanUtil.setFieldValue(roleData.getCtrlType(), roleData.getCtrlData(), authBase);
            }
        } else {
            authBase.setLoginName(ShiroManager.getLoginName());
        }
    }
}
