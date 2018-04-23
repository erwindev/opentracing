package com.erwindev.opentracing.server3.service;

import org.springframework.stereotype.Service;

@Service
public class BackendService {

    public String process(){
        return "Hello World from Server 3";
    }

}
