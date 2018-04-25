package com.erwindev.opentracing.server1.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {
    @Pointcut("execution(* com.erwindev.opentracing.server1.controller.*.*(..))")
    public void logging() {}

    @Around("logging()")
    public Object logging(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        System.out.println("Before " + thisJoinPoint.getSignature());
        Object ret = thisJoinPoint.proceed();
        System.out.println("After " + thisJoinPoint.getSignature());

        return ret;
    }
}
