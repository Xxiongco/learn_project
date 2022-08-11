package com.example.learn_spring_boot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    public static String LOCAL_HELLO_URL = "http://localhost:8081/hello";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${my.name}")
    private String name;

    @GetMapping
    public String getHello(String message) {

        System.out.println(name);

        log.info(message);
        return "ok hello, i get " + message;
    }

    /**
     *  RestTemplate使用这个HttpURLConnection（和JDK一样）
     */
    @GetMapping("/rest")
    public String getHelloByRestTemplate(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        String result = restTemplate.getForObject(LOCAL_HELLO_URL, String.class, map);
        log.info(result);
        return result;
    }


}
