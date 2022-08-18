package com.example.learn_spring_boot.quartz;


import com.example.learn_spring_boot.quartz.domian.JobInfo;
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
                .withIdentity("triggerKey.name","triggerKey.group")
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
                .withIdentity("jobKey.name","jobKey.group")
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
        scheduler.triggerJob(JobKey.jobKey("jobKey.name","jobKey.group"));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

    @GetMapping("/create")
    public void createJob(JobInfo jobInfo) throws Exception {
        JobUtil.createJob(scheduler,
                QuartzUtil.buildJobDetail(MyTestJob.class, JobUtil.getJobKeyName("1"), JobUtil.getJobKeyGroup("1")),
                QuartzUtil.buildCronTrigger(JobUtil.getTriggerKey("1"), JobUtil.getTriggerGroup("1"), JobUtil.getCorn()));
    }

    @GetMapping("/create2")
    public void createJob2(JobInfo jobInfo) throws Exception {
        JobUtil.createJob(scheduler,
                QuartzUtil.buildJobDetail(MyTestJob2.class, JobUtil.getJobKeyName("2"), JobUtil.getJobKeyGroup("2")),
                QuartzUtil.buildCronTrigger(JobUtil.getTriggerKey("2"), JobUtil.getTriggerGroup("2"), JobUtil.getCorn()));
    }

    @GetMapping("/process")
    public void processJob(JobInfo jobInfo) throws Exception {
        JobUtil.processJob(scheduler, JobKey.jobKey(JobUtil.getJobKeyName("1"), JobUtil.getJobKeyGroup("1")));
    }

    @GetMapping("/pause")
    public void pauseJob(JobInfo jobInfo) throws Exception {
        JobUtil.pauseJob(scheduler, JobKey.jobKey(JobUtil.getJobKeyName("1"), jobInfo.getJobKeyGroup()));
    }

    @GetMapping("/resume")
    public void resumeJob(JobInfo jobInfo) throws Exception {
        JobUtil.resumeJob(scheduler, JobKey.jobKey(jobInfo.getJobKeyName(), jobInfo.getJobKeyGroup()));
    }

    @GetMapping("/remove")
    public void removeJob(JobInfo jobInfo) throws Exception {
        JobUtil.deleteJob(scheduler,
                JobKey.jobKey(jobInfo.getJobKeyName(), jobInfo.getJobKeyGroup()),
                TriggerKey.triggerKey(jobInfo.getTriggerKey(), jobInfo.getTriggerGroup()));
    }

}
