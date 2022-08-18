package com.example.learn_spring_boot.quartz;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import org.quartz.*;


public class JobUtil {

    public static String JOB_KEY_NAME = "jobKeyName-%s";
    public static String JOB_KEY_GROUP = "jobKeyGroup-%s";

    public static String TRIGGER_KEY = "triggerKey-%s";
    public static String TRIGGER_GROUP = "triggerGroup-%s";

    public static String CORN = "0/5 * * * * ?";

    public static String getJobKeyName(String name) {
        return String.format(JOB_KEY_NAME, name);
    }

    public static String getJobKeyGroup(String name) {
        return String.format(JOB_KEY_GROUP, name);
    }

    public static String getTriggerKey(String name) {
        return String.format(TRIGGER_KEY, name);
    }

    public static String getTriggerGroup(String name) {
        return String.format(TRIGGER_GROUP, name);
    }

    public static String getCorn() {
        return CORN;
    }


    public static void createJob(Scheduler scheduler, JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    public static void processJob(Scheduler scheduler, JobKey jobKey) throws SchedulerException {
        scheduler.triggerJob(jobKey);
    }

    public static void pauseJob(Scheduler scheduler, JobKey jobKey) throws SchedulerException {
        scheduler.pauseJob(jobKey);
    }

    public static void deleteJob(Scheduler scheduler, JobKey jobKey, TriggerKey triggerKey) throws SchedulerException {
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(jobKey);
    }

    public static void resumeJob(Scheduler scheduler, JobKey jobKey) throws SchedulerException {
        scheduler.resumeJob(jobKey);
    }

}
