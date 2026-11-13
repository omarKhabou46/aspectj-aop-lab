package com.enset.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@Aspect
public class LoggingAspect {
    private final Logger logger = Logger.getLogger(LoggingAspect.class.getName());
    private long t1, t2;

    public LoggingAspect() throws IOException {
        logger.addHandler(new FileHandler("log.xml"));
        logger.setUseParentHandlers(false);
    }

    @Pointcut("execution(* com.enset.metier.MetierImpl.*(..))")
    public void pc1() {}

    @Before("pc1()")
    public void before(JoinPoint joinPoint) {
        t1 = System.currentTimeMillis();
        logger.info("before " + joinPoint.getSignature());
    }

    @After("pc1()")
    public void after(JoinPoint joinPoint) {
        logger.info("after " + joinPoint.getSignature());
        t2 = System.currentTimeMillis();
        logger.info("duree d'execution : " + (t2 - t1));
    }

}
