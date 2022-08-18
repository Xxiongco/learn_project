package com.example.learn_spring_boot.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MyRunner implements ApplicationRunner {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Integer expireTime = 300;

        redisTemplate.opsForValue().set("hello","hello",expireTime,TimeUnit.SECONDS);


    }
}
