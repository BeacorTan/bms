package com.common.quartz;

import com.common.framework.util.HelpUtils;
import com.base.quarz.model.SystemScheduler;
import org.quartz.Job;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SchedulerManager extends HelpUtils {

    @Resource
    private SchedulerUtil schedulerUtil;

    /**
     * 添加
     *
     * @param scheduler
     */
    public void addScheduler(SystemScheduler scheduler) {
        addOrModify(scheduler);
    }

    public void start() {
        schedulerUtil.startJobs();
    }

    /**
     * 添加或修改
     *
     * @param scheduler
     */
    private void addOrModify(SystemScheduler scheduler) {
        // job类处理
        Class<? extends Job> jobclass = verify(scheduler);
        // 添加到调度器
        if (!HelpUtils.isEmpty(jobclass)) {
            if (schedulerUtil.isExist(getJobName(scheduler))) {// 存在
                schedulerUtil.modifyJobTime(getJobName(scheduler), scheduler.getCron(),
                        schedulerUtil.getJobDataMap(scheduler.getJobParams()));
            } else {// 不存在
                schedulerUtil.addJob(getJobName(scheduler), jobclass, scheduler.getCron(),
                        schedulerUtil.getJobDataMap(scheduler.getJobParams()));
            }
            pauseJob(scheduler);
        }
    }

    /**
     * 如果isStart=false暂停一个任务，否则开启一个任务
     *
     * @param scheduler
     */
    public void pauseJob(SystemScheduler scheduler) {
        String jobName = getJobName(scheduler);
        Integer start = scheduler.getStart();
        if (start == 1) {// 开启一个任务
            schedulerUtil.resumeJob(jobName);
        } else if (start == 0) {// 暂停一个任务
            schedulerUtil.pauseJob(jobName);
        }
    }

    private static String getJobName(SystemScheduler scheduler) {
        return scheduler.getId();
    }

    /**
     * 校验
     *
     * @param systemScheduler
     * @return
     */
    @SuppressWarnings("unchecked")
    public Class<? extends Job> verify(SystemScheduler systemScheduler) {
        try {
            // cron 表达式验证
            schedulerUtil.cronVerify(systemScheduler.getCron());
            // jobclass验证
            Class<?> clazz = Class.forName(systemScheduler.getJobClass());
            Object instance = clazz.newInstance();
            if (instance instanceof ParamsVerify && isEmpty(systemScheduler.getId())) {
                ParamsVerify paramsVerify = (ParamsVerify) instance;

                paramsVerify.verify(schedulerUtil.getJobDataMap(systemScheduler.getJobParams()));// 校验
            }
            if (instance instanceof Job) {
                return (Class<? extends Job>) clazz;
            }
            return null;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            //logger.error(e.getMessage(), e);// 记录日志
            return null;
        }
    }

    /**
     * 删除
     *
     * @param scheduler
     */
    public void deleteScheduler(SystemScheduler scheduler) {
        schedulerUtil.removeJob(getJobName(scheduler));
    }

    /**
     * 修改调度器cron
     *
     * @param scheduler
     */
    public void updateScheduler(SystemScheduler scheduler) {
        addOrModify(scheduler);
    }
}
