package com.enset.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectTest {

    @Pointcut("execution(* com.enset.Main.main(..))")
    public void pc1() {}

    @Before("pc1()")
    public void before() {
        System.out.println("Before main()");
    }
}