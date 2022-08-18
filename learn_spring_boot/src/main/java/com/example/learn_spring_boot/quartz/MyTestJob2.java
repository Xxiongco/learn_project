package com.example.learn_spring_boot.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@Slf4j
// 开启串行化
// @DisallowConcurrentExecution
public class MyTestJob2 implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info(DateUtil.formatDateInfo(new Date()) + "  ===========    my test job  2   ==================");
    }
}
