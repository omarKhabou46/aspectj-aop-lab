package com.enset.aspects;

import com.enset.metier.MetierImpl;
import com.enset.models.Compte;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PatchAspect {

    @Pointcut("execution(* com.enset.metier.MetierImpl.retirer(..))")
    public void pc1() {}

    @Around("pc1()&& args(code, montant)")
    public Object aroundAdvice(Long code, double montant, ProceedingJoinPoint proceedingJoinPoint, JoinPoint joinPoint) throws Throwable {
        MetierImpl metier = (MetierImpl) joinPoint.getTarget();
        Object result = null;
        Compte compte = metier.getCompte(code);
        if (compte.getBalance() > montant) {
            result = proceedingJoinPoint.proceed();
        }else {
            System.out.println("-------- cette operation est impossible d'etre effectuer -----------");
        }
        return result;
    }
}
