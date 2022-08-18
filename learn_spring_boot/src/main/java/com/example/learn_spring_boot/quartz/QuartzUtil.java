package com.example.learn_spring_boot.quartz;

import org.quartz.*;

public class QuartzUtil {

    public static CronTrigger buildCronTrigger(String triggerKey, String triggerGroup, String cron) {
        CronTrigger trigger = TriggerBuilder.newTrigger()
                /** 给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，
                 * 可以通过jobExecutionContext.getTrigger().getJobDataMap().get("orderNo")获取值  */
//                .usingJobData("orderNo", "123456")
                /** 添加认证信息 */
                .withIdentity(triggerKey,triggerGroup)
                /** 立即生效 */
                .startNow()
                /** 添加执行规则 */
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();//执行

        return trigger;

    }

    public static JobDetail buildJobDetail(Class<? extends Job> jobClass, String jobKeyName, String jobKeyGroup) {

        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                /**给当前JobDetail添加参数，K V形式*/
//                .usingJobData("name","zy")
//                .usingJobData("age",23)
                /**添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种*/
                .withIdentity(jobKeyName,jobKeyGroup)
                .build();//执行

        return jobDetail;
    }

}
