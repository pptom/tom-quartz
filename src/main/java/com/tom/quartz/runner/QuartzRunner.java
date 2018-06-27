package com.tom.quartz.runner;

import com.tom.quartz.entity.ScheduleJob;
import com.tom.quartz.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Mr Tom
 * @date 2018/6/27
 * @email ptomjie@gmail.com
 * @description
 * @since 2018/6/27
 */
@Component
public class QuartzRunner implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(QuartzRunner.class);

    @Autowired
    private JobService jobService;

    @Override
    public void run(String... args) throws Exception {
        List<ScheduleJob> jobList = jobService.getAllJob();
        if (jobList.size() == 0) {
            ScheduleJob scheduleJob = new ScheduleJob();
            scheduleJob.setJobName("testJob");
            scheduleJob.setJobGroup("testJobGroup");
            scheduleJob.setTriggerName("testTrigger");
            scheduleJob.setTriggerGroup("testTriggerGroup");
            scheduleJob.setDescription("This is a test job");
            scheduleJob.setClassName("com.tom.quartz.job.TestJob");
            //每5秒执行一次
            scheduleJob.setCronExpression("0/5 * * * * ?");
            try {
                jobService.add(scheduleJob);
                logger.info("初始化测试任务完成");
            } catch (Exception e) {
                logger.error("初始化测试任务异常!MSG:{}", e.getMessage());
            }
        }

    }
}
