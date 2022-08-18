package com.example.learn_spring_boot.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.Objects;


@Slf4j
// 开启串行化
@DisallowConcurrentExecution
public class MyTestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info( DateUtil.formatDateInfo(new Date()) + "  ===========    my test job     ==================");
        for (int i = 0 ; i < 10 ; i ++) {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("job1" + " : " + i);
        }
    }
}
