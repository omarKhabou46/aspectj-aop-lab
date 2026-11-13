package com.enset.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class TestAspectJ {

    @Pointcut("execution(* com.enset.Main.main(..))")
    public void pc() {}

    @Around("pc()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("---------------- around ----------------");
        System.out.println("---------------- before ----------------");
        proceedingJoinPoint.proceed();
        System.out.println("---------------- after ----------------");
    }

    @Before("pc()")
    public void before() {
        System.out.println("before main method");
    }

    @After("pc()")
    public void after() {
        System.out.println("after main method");
    }
}