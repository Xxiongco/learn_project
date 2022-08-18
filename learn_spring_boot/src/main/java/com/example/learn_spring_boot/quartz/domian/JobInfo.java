package com.example.learn_spring_boot.quartz.domian;


import lombok.Data;

@Data
public class JobInfo {

    private String jobKeyName;

    private String jobKeyGroup;

    private String triggerKey;

    private String triggerGroup;

    private String cron;

}
