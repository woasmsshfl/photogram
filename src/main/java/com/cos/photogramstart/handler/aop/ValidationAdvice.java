package com.cos.photogramstart.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component // IoC 컨테이너에 등록해주는 어노테이션들의 최상위 객체
@Aspect // AOP처리를 할 수 있게 만들어주는 어노테이션
public class ValidationAdvice {
    
    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("==========================================");
        System.out.println("WEB API Controller 실행 : Data Response");
        System.out.println("==========================================");
        return proceedingJoinPoint.proceed();
    }

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("==========================================");
        System.out.println("WEB Controller 실행 : File Response");
        System.out.println("==========================================");
        return proceedingJoinPoint.proceed();
    }
}
