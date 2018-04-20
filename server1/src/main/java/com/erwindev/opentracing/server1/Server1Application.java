package com.erwindev.opentracing.server1;

import com.erwindev.opentracing.server1.util.TraceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Server1Application {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
		return restTemplateBuilder.build();
	}

	@Bean
	public io.opentracing.Tracer tracer(){
		//io.opentracing.Tracer tracer = TraceUtil.jaegerTracer("tutorial-service3");
		io.opentracing.Tracer tracer = TraceUtil.zipkinTracer("tutorial-service1");
		return tracer;
	}

	public static void main(String[] args) {
		SpringApplication.run(Server1Application.class, args);
	}
}
