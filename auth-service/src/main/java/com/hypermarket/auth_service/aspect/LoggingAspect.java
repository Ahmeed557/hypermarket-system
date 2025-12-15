package com.hypermarket.auth_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
@Slf4j 
public class LoggingAspect {
    
    @Pointcut("execution(* com.hypermarket.user.service.*.*(..))")
    public void serviceMethods() {}
    
    
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("⏳ Executing: {} with arguments: {}", 
                joinPoint.getSignature().getName(), 
                Arrays.toString(joinPoint.getArgs()));
    }
    
   
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("✅ Method {} executed successfully", 
                joinPoint.getSignature().getName());
    }
    
    
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("❌ Exception in {}: {}", 
                joinPoint.getSignature().getName(), 
                exception.getMessage());
    }
    
 
    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();  // تنفيذ الـ method
        long executionTime = System.currentTimeMillis() - start;
        log.info("⏱️ Method {} executed in {} ms", 
                joinPoint.getSignature().getName(), 
                executionTime);
        return result;
    }
}