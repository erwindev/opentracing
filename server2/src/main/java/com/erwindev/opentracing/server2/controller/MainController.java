package com.erwindev.opentracing.server2.controller;

import com.erwindev.opentracing.server2.service.BackendService;
import com.erwindev.opentracing.server2.util.TraceUtil;
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
        Scope scope = TraceUtil.startServerSpan(tracer, headers, "hello");

        scope.span().setTag("server2", "hello");
        String body = backendService.process();

        //scope.span().log(ImmutableMap.of("event", "string-format", "value", "Hello World from Server 2 <----> " + body));
        scope.span().finish();


        return "Hello World from Server 2 <----> " + body;
    }
}
