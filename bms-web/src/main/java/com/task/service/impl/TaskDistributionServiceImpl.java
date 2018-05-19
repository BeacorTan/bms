package com.task.service.impl;

import com.base.role.model.RoleData;
import com.base.role.service.RoleDataService;
import com.base.util.AuthManager;
import com.task.mapper.TaskDistributionMapper;
import com.task.model.ImportTask;
import com.task.model.TaskCustomer;
import com.task.model.TaskDistributionRecordVO;
import com.task.model.TaskDistributionVO;
import com.task.model.TaskSearchConditionVO;
import com.task.service.ITaskDistributionService;
import com.common.framework.base.BaseMapper;
import com.common.framework.base.BaseServiceImpl;
import com.common.framework.excel.ExcelUtil;
import com.common.framework.util.BeanUtil;
import com.common.framework.util.PageBean;
import com.common.framework.util.PagedResult;
import com.common.framework.util.ResponseJson;
import com.common.framework.util.ServiceUtil;
import com.common.shiro.ShiroManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BoSongsh
 * @create 2018-04-23 14:53
 **/
@Service
public class TaskDistributionServiceImpl extends BaseServiceImpl<TaskDistributionVO> implements ITaskDistributionService {

    private Logger LOGGER = LoggerFactory.getLogger(TaskDistributionServiceImpl.class);

    @Autowired
    private TaskDistributionMapper taskDistributionMapper;

    @Resource
    private RoleDataService roleDataService;

    @Autowired
    private AuthManager authManager;

    @Override
    public ResponseJson distribute(MultipartFile file, String taskID) {

        List<ImportTask> importTasks = null;

        boolean flag = true;
        String msg = "分配成功";

        if (StringUtils.isEmpty(taskID)) {
            return ServiceUtil.getResponseJson("任务ID为空", false);
        }

        try {
            importTasks = ExcelUtil.importDataFromExcel(ImportTask.class, file);
        } catch (IOException e) {
            flag = false;
            LOGGER.error("TaskDistributionServiceImpl.distribute() IOException异常：{}", e);
            msg = "服务异常请联系管理员";
        } catch (IllegalAccessException e) {
            flag = false;
            LOGGER.error("TaskDistributionServiceImpl.distribute() IllegalAccessException异常：{}", e);
            msg = "服务异常请联系管理员";
        } catch (InstantiationException e) {
            flag = false;
            LOGGER.error("TaskDistributionServiceImpl.distribute() InstantiationException异常：{}", e);
            msg = "服务异常请联系管理员";
        }

        if (CollectionUtils.isEmpty(importTasks)) {
            LOGGER.error("TaskDistributionServiceImpl.distribute()未上传数据");
            msg = "未上传数据";
            flag = false;
        }

        if (!flag) {
            return ServiceUtil.getResponseJson(msg, flag);
        }

        String userNo;
        String empCode;
        TaskDistributionVO taskDistributionVO;
        List<String> errorUser = new ArrayList<String>(100);
//        for (ImportTask importTask : importTasks) {
//            userNo = importTask.getUserNo();
//            empCode = customerDistributionMapper.selectEmpCodeByUserNo(userNo);
//            if (StringUtils.isNotBlank(empCode)) {
//                taskDistributionVO = new TaskDistributionVO(taskID, 0);
//                taskDistributionVO.setEmpCode(empCode);
//                taskDistributionVO.setUserNo(userNo);
//                ModelUtil.insertInit(taskDistributionVO);
//                taskDistributionMapper.insertSelective(taskDistributionVO);
//            } else {
//                errorUser.add(userNo);
//            }
//        }
        return ServiceUtil.getResponseJson(msg, flag, errorUser);
    }

    @Override
    public PagedResult<TaskDistributionRecordVO> query(PageBean pageBean, TaskDistributionRecordVO taskDistributionRecordVO) {
        ServiceUtil.startPage(pageBean);
        List<TaskDistributionRecordVO> taskDistributionRecordVOs = taskDistributionMapper.selectCondition(taskDistributionRecordVO);
        return BeanUtil.toPagedResult(taskDistributionRecordVOs);
    }

    @Override
    public PagedResult<TaskCustomer> queryUser(PageBean pageBean, TaskSearchConditionVO taskSearchConditionVO) {

        String loginName = ShiroManager.getLoginName();
        List<RoleData> roleDataList = roleDataService.selectByLoginName(loginName);

        try {
            authManager.setAuth(taskSearchConditionVO);
        } catch (NoSuchFieldException e) {
            LOGGER.error("TaskDistributionServiceImpl.queryUser() NoSuchFieldException授权失败：{}", e);
            return null;
        } catch (IllegalAccessException e) {
            LOGGER.error("TaskDistributionServiceImpl.queryUser() IllegalAccessException授权失败：{}", e);
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String beginDate = taskSearchConditionVO.getBeginDate();

        if (StringUtils.isNotBlank(beginDate)) {
            String[] beginDateStr = beginDate.split(" - ");
            try {
                taskSearchConditionVO.setBeginTaskBeginDate(df.parse(beginDateStr[0]));
                taskSearchConditionVO.setEndTaskBeginDate(df.parse(beginDateStr[1]));
            } catch (ParseException e) {
                LOGGER.error("TaskDistributionServiceImpl.queryUser()时间转换异常：{}", e);
            }
        }

        String endDate = taskSearchConditionVO.getEndDate();
        if (StringUtils.isNotBlank(endDate)) {
            String[] endDateStr = endDate.split(" - ");
            try {
                taskSearchConditionVO.setBeginTaskEndDate(df.parse(endDateStr[0]));
                taskSearchConditionVO.setEndTaskEndDate(df.parse(endDateStr[1]));
            } catch (ParseException e) {
                LOGGER.error("TaskDistributionServiceImpl.queryUser()时间转换异常：{}", e);
            }
        }


        ServiceUtil.startPage(pageBean);
        List<TaskCustomer> taskCustomers = taskDistributionMapper.selectUserByCondition(taskSearchConditionVO);
        return BeanUtil.toPagedResult(taskCustomers);
    }

    @Override
    public int countTask(TaskSearchConditionVO taskSearchConditionVO) {
        try {
            authManager.setAuth(taskSearchConditionVO);
        } catch (NoSuchFieldException e) {
            LOGGER.error("TaskDistributionServiceImpl.countTask() NoSuchFieldException授权失败：{}", e);
            return 0;
        } catch (IllegalAccessException e) {
            LOGGER.error("TaskDistributionServiceImpl.countTask() IllegalAccessException授权失败：{}", e);
            return 0;
        }
        return taskDistributionMapper.countTask(taskSearchConditionVO);
    }

    @Override
    public BaseMapper getMapper() {
        return this.taskDistributionMapper;
    }
}
