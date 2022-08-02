package com.panda.learn_spring_boot_kafka.comtroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/kafka/normal/{msg}")
    public void sendMessage(@PathVariable("msg") String msg) {
        kafkaTemplate.send("hello-world", msg);
    }
}

