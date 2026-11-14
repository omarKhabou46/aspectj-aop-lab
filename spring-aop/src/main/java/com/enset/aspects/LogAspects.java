package com.enset.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
@EnableAspectJAutoProxy
public class LogAspects {
   private final Logger logger = Logger.getLogger(LogAspects.class.getName());

    @Around("@annotation(com.enset.annotations.Log)")
    public Object logBeforeEachMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long t1 = System.currentTimeMillis();
        logger.info("before logging aspects ... " + proceedingJoinPoint.getSignature());
        Object result = proceedingJoinPoint.proceed();
        logger.info("after logging aspects ... " + proceedingJoinPoint.getSignature());
        long t2 = System.currentTimeMillis();
        logger.info("duration : " + (t2 - t1));
        return result;
    }

}
