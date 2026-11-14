package com.enset.aspects;

import com.enset.annotations.SecuredByAspect;
import com.enset.metier.SecurityContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


@EnableAspectJAutoProxy
@Aspect
@Component
public class SecuredAspect {

    @Around(value = "@annotation(securedByAspect)", argNames = "pjp,securedByAspect")
    public Object authenticate(ProceedingJoinPoint pjp, SecuredByAspect securedByAspect) throws Throwable {
        System.out.println("before secured annotation .... ");
        String[] roles = securedByAspect.roles();
        boolean authorize = false;
        for (String r : roles) {
            if (SecurityContext.hasRole(r)) {
                authorize = true;
                break;
            }

        }
        if (authorize) {
            return pjp.proceed();
        }else {
          throw new RuntimeException("unauthorized >>> 403");
        }
    }
}
