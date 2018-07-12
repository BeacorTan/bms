package com.base.util;

import com.base.role.model.RoleData;
import com.base.role.service.RoleDataService;
import com.common.framework.util.BeanUtil;
import com.common.model.AuthBase;
import com.common.shiro.ShiroManager;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

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
