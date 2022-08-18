package com.example.learn_spring_boot.quartz;


import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@RequestMapping("/quartz")
@Slf4j
public class QuartzController implements ApplicationRunner {

    // 默认是 StdScheduler
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private DataSource dataSource;

    @GetMapping
    public void startJob() throws SchedulerException {

        CronTrigger trigger = TriggerBuilder.newTrigger()
                /** 给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，
                 * 可以通过jobExecutionContext.getTrigger().getJobDataMap().get("orderNo")获取值  */
                .usingJobData("orderNo", "123456")
                /** 添加认证信息 */
                .withIdentity("我是name","我是group")
                /** 立即生效 */
                .startNow()
                /** 添加执行规则 */
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                .build();//执行

        JobDetail jobDetail = JobBuilder.newJob(MyTestJob.class)
                /**给当前JobDetail添加参数，K V形式*/
                .usingJobData("name","zy")
                .usingJobData("age",23)
                /**添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种*/
                .withIdentity("我是name","我是group")
                .build();//执行

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();


        /**
         *
         * 前边创建的时候传入了任务的认证信息
         * .withIdentity(key,group)
         *
         * 删除一个job
         * scheduler.pauseTrigger(TriggerKey.triggerKey("我是刚才写的name","我是刚才写的group"));
         * scheduler.resumeTrigger(TriggerKey.triggerKey("我是刚才写的name","我是刚才写的group"));
         * scheduler.deleteJob(JobKey.jobKey("我是刚才写的name","我是刚才写的group"));
         *
         * 执行一次任务。（手动）
         * scheduler.triggerJob(JobKey)
         *
         */
    }

    @GetMapping("/again")
    public void startAnotherJob() throws SchedulerException {
        scheduler.triggerJob(JobKey.jobKey("我是name","我是group"));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
