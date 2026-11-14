package com.enset.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Scanner;

@Aspect
public class SecurityAspect {

    private final String username = "root";
    private final String password = "password";
    private final Scanner scanner = new Scanner(System.in);

    @Pointcut("execution(* com.enset.Main.start(..))")
    public void pc1() {}

    @Around("pc1()")
    public Object aroundSecurity(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("username : ");
        String name = scanner.next();
        System.out.println("password : ");
        String pw = scanner.next();
        Object result = null;
        if (name.equals(username) && pw.equals(password)) {
            result = proceedingJoinPoint.proceed();
        }else {
            throw new RuntimeException("vous n'etes pas authentifier");
        }
        return result;
    }

}
