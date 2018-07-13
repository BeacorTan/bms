package com.common.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * 测试类
 *
 */
@Component
public class SchedulerTest implements Job {

    private Logger logger =  LoggerFactory.getLogger(SchedulerTest.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.error("====================================SchedulerTest================================================");
    }
}
