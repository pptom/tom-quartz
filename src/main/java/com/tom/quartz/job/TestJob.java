package com.tom.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author Mr Tom
 * @date 2018/5/14
 * @email ptomjie@gmail.com
 * @description 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
 * @since 2018/5/14
 */
@DisallowConcurrentExecution
public class TestJob implements Job,Serializable {

    private final static Logger logger = LoggerFactory.getLogger(TestJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("测试任务运行中!");
    }
}
