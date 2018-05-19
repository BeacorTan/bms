//package com.cl.common.quartz.jobs;
//
//import com.cl.cm.base.user.service.UserService;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * 测试类
// *
// *
// * @date 2017年02月23日
// */
//@Component
//public class SchedulerTest implements Job {
//    @Resource
//    private UserService userService;
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        try {
//            System.out.println(userService.selectAll().size());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
