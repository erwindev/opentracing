package com.erwindev.opentracing.server3.controller;

import com.erwindev.opentracing.server3.service.BackendService;
import com.erwindev.opentracing.server3.util.TraceUtil;
import com.google.common.collect.ImmutableMap;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private BackendService backendService;

    @Autowired
    private Tracer tracer;

    @RequestMapping("/hello")
    public String hello(@RequestHeader HttpHeaders headers){
        Scope scope = TraceUtil.startServerSpan(tracer, headers, "/hello");

        scope.span().setTag("server3", "hello");

        String body = backendService.process();

        //scope.span().log(ImmutableMap.of("event", "string-format", "value", "Hello World from Server 3"));
        scope.span().finish();

        return body;
    }
}
