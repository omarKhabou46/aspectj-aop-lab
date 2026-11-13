package com.enset.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.logging.Logger;

@Aspect
public class LoggingAspect {
    private long t1, t2;

    @Pointcut("execution(* com.enset.metier.MetierImpl.*(..))")
    public void pc1() {}

    @Before("pc1()")
    public void before(JoinPoint joinPoint) {
        t1 = System.currentTimeMillis();
        System.out.println("before " + joinPoint.getSignature());
    }

    @After("pc1()")
    public void after(JoinPoint joinPoint) {
        System.out.println("after " + joinPoint.getSignature());
        t2 = System.currentTimeMillis();
        System.out.println("duree d'execution : " + (t2 - t1));
    }

}
