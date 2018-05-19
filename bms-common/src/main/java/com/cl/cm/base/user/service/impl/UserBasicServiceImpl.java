package com.cl.cm.base.user.service.impl;

import com.cl.cm.base.user.mapper.UserBasicMapper;
import com.cl.cm.base.user.mapper.UserRoleMapper;
import com.cl.cm.base.user.model.UserBasic;
import com.cl.cm.base.user.model.UserBasicVO;
import com.cl.cm.base.user.model.UserRoleMap;
import com.cl.cm.base.user.service.UserBasicService;
import com.cl.common.framework.util.BeanUtil;
import com.cl.common.framework.util.ModelUtil;
import com.cl.common.framework.util.PageBean;
import com.cl.common.framework.util.PagedResult;
import com.cl.common.framework.util.ServiceUtil;
import com.cl.common.shiro.EncryptPwd;
import com.cl.common.shiro.ShiroManager;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service(value = "userBasicService")
public class UserBasicServiceImpl implements UserBasicService {

    @Resource
    private UserBasicMapper userBasicMapper;

    @Resource
    private UserRoleMapper userRoleMapper;


    @Transactional
    @Override
    public void userAdd(UserBasicVO userVO) {
        if (userVO == null) {
            return;
        }
        // 新增默认密码为123456
        userVO.setPassword("123456");
        Date currDate = new Date();
        String loginName = ShiroManager.getLoginName();
//        userVO.setCreateTime(currDate.getTime());
//        userVO.setCreateUser(loginName);
//        userVO.setOperator(loginName);
//        userVO.setOperatTime(currDate);
        new EncryptPwd().encryptPassword(userVO);
//        userVO.setIsActive("1");
//        userVO.setIsFrozen("0");
//        userVO.setErrCountPw(1);
//        userVO.setChannelCode("9999");
//        insert into t_user_basic(id,login_name,real_name,salt,password,is_active,channel_code,IS_FROZEN,ERR_COUNT_PW,CREATE_USER)
//        values('2','000000','000000','d56b05ae01e7216faa08122d0bd2baa9','cc411ffc8decb08ea39723453cd84fe2','1','9999','0'，1,'admin')
        userBasicMapper.insert(userVO);

        List<String> roleCodes = userVO.getRoleCodes();
        if (roleCodes == null || roleCodes.isEmpty()) {
            return;
        }

        loginName = userVO.getLoginName();
        UserRoleMap userRoleMap;
        for (String role : roleCodes) {
            if (role == null || "".equals(role)) {
                continue;
            }
            userRoleMap = new UserRoleMap(role, loginName);
            ModelUtil.insertInit(userRoleMap);
            userRoleMapper.insert(userRoleMap);
        }

    }


    @Transactional
    @Override
    public void updateUser(UserBasicVO userVO) {
        if (userVO == null) {
            return;
        }
//        userVO.setOperatTime(new Date());
//        userVO.setOperator(ShiroManager.getLoginName());
        UserBasic user = userVO;
        String password = userVO.getPassword();
        if (!StringUtils.isEmpty(password)) {
            new EncryptPwd().encryptPassword(user);
        }
        userBasicMapper.updateUserByID(user);

        List<String> roleCodes = userVO.getRoleCodes();
        if (CollectionUtils.isNotEmpty(roleCodes)) {
            String loginName = user.getLoginName();
            UserRoleMap userRoleMap = null;
            userRoleMapper.delete(new UserRoleMap(loginName));
            for (String roleCode : roleCodes) {
                userRoleMap = new UserRoleMap(roleCode, loginName);
                ModelUtil.insertInit(userRoleMap);
                userRoleMapper.insert(userRoleMap);
            }
        }
    }

    @Override
    public UserBasic selectByLoginName(String loginName) {
        return userBasicMapper.selectByLoginName(loginName);
    }

    @Override
    public UserBasicVO selectByPrimaryKey(String id) {
        return userBasicMapper.selectByID(id);
    }

    @Override
    public PagedResult<UserBasic> selectPageList(PageBean pageBean, UserBasicVO user) {
        ServiceUtil.startPage(pageBean);
        return BeanUtil.toPagedResult(userBasicMapper.selectListByUser(user));
    }


    @Override
    public boolean isExitLoginName(String loginName) {
        int result = userBasicMapper.isExitLoginName(loginName);
        if (result == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteByIDs(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        userBasicMapper.deleteByIDs(ids);
    }
}
