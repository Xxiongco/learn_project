package com.panda.learn_spring_boot_kafka.comtroller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"hello-world"})
    public void onoMsg(ConsumerRecord<?,?> record) {
        System.out.println("简单消费： " + record.topic() + "-" + record.partition() + "- " + record.value());
    }
}

