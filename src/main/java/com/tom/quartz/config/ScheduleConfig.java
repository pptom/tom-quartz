package com.tom.quartz.config;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr Tom
 * @date 2018/6/25
 * @email ptomjie@gmail.com
 * @description
 * @since 2018/6/25
 */
@Configuration
public class ScheduleConfig {
    @Bean
    public SchedulerFactoryBeanCustomizer customizer() {
        return (schedulerFactoryBean)->{
            schedulerFactoryBean.setStartupDelay(10);
            schedulerFactoryBean.setOverwriteExistingJobs(true);
            schedulerFactoryBean.setSchedulerName("TASK_EXECUTOR");
        };
    }
}
