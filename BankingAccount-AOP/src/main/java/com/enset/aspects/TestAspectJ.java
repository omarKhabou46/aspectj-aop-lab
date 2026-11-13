package com.enset;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TestAspectJ {

    @Pointcut("execution(* com.enset.Main.main(..))")
    public void pc() {}

    @Before("pc()")
    public void before() {
        System.out.println("before main method");
    }

    @After("pc()")
    public void after() {
        System.out.println("after main method");
    }
}