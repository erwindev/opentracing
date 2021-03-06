package com.erwindev.opentracing.server2.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BackendService {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(BackendService.class);

    @HystrixCommand(fallbackMethod = "handleError", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")})
    public String process(){
        logger.info("Calling server3");
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:9083/hello", String.class);
        logger.info("Returned from server3");
        return response.getBody();
    }

    private String handleError() {
        logger.info("server3 is down!");
        return "Degraded Hello Service (server3 is down)!";
    }
}
