package com.erwindev.opentracing.server3;

import com.erwindev.opentracing.server3.util.TraceUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Server3Application {

	@Bean
	public io.opentracing.Tracer tracer(){
		//io.opentracing.Tracer tracer = TraceUtil.jaegerTracer("tutorial-service3");
		io.opentracing.Tracer tracer = TraceUtil.zipkinTracer("tutorial-service3");
		return tracer;
	}

	public static void main(String[] args) {
		SpringApplication.run(Server3Application.class, args);
	}
}
