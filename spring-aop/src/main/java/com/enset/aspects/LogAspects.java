package com.enset.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class LogAspects {


    @Around("execution(* com.enset.metier.MetierImpl.*(..))")
    public Object logBeforeEachMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long t1 = System.currentTimeMillis();
        System.out.println("from logging aspects ... " + proceedingJoinPoint.getSignature());
        Object result = proceedingJoinPoint.proceed();
        long t2 = System.currentTimeMillis();
        System.out.println("execution time : " + (t2 - t1));
        return result;
    }

}
