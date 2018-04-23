package com.erwindev.opentracing.server2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BackendService {

    @Autowired
    private RestTemplate restTemplate;

    public String process(){
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:9083/hello", String.class);
        return response.getBody();
    }
}
