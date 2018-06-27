# SpringBoot 集成 Quartz Starter实现动态任务
在网上已经有许多例子和demo是实现动态任务管理的。本项目是按照spring boot提供的quartz starter对quartz动态任务进行整合。

- 本次项目实现了：

1. 查看所有的定时任务，并可触发运行指定任务
2. 动态地暂停或恢复定时任务
3. 动态地修改定时任务的频率
4. 定时任务和调度计划可以持久化到数据库中（修改的频率也是持久化的）

- **快速启动**
1. 执行sql目录下的sql文件生成quartz表及动态管理用的数据表
2. 修改application.properties配置文件中的数据库连接信息
3. 启动TomQuartzApplication中的main方法
4. 查看测试任务是否正常打印日志

- **任务管理页面：http://localhost:8080/job**
---

- 关于定制化SchedulerFactoryBean的例子如下:
1. 参考接口的代码及说明
```
/**
 * Callback interface that can be implemented by beans wishing to customize the Quartz
 * {@link SchedulerFactoryBean} before it is fully initialized, in particular to tune its
 * configuration.
 *
 * @author Vedran Pavic
 * @since 2.0.0
 */
@FunctionalInterface
public interface SchedulerFactoryBeanCustomizer {

	/**
	 * Customize the {@link SchedulerFactoryBean}.
	 * @param schedulerFactoryBean the scheduler to customize
	 */
	void customize(SchedulerFactoryBean schedulerFactoryBean);

}
```
2. 实现
```
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
```
3. spring自动注入 
- 在QuartzAutoConfiguration中会找到SchedulerFactoryBeanCustomizer所有实现的bean并遍历执行customizer方法（个人理解）


# 参考资料

1. [spring-boot官方文档：boot-features-quartz](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-quartz.html)
    最新版本的spring-boot集成quartz的资料，参考此文档配置SchedulerFactoryBean。
2. [SpringBoot 中使用 Quartz 定时任务](https://blog.csdn.net/u013360850/article/details/79318343)
    主要参考此文档代码开发，完善暂停启动功能。(文中其实可以不用手动注入job，具体参考我的代码)。
3. [quartz的misfire详解](https://dzone.com/articles/quartz-scheduler-misfire)
    参考此文，解决了quartz启动时连续多次触发任务(实际可能会需要这种操作，具体配置参考misfire的说明)。
4. [quartz暂停及恢复任务解决恢复时一咕噜把未执行的全补回来的问题](http://jdkleo.iteye.com/blog/2169949)
    参考此文，重新配置misfireThreshold时间。