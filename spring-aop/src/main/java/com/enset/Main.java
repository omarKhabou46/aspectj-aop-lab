package com.enset;

import com.enset.metier.IMetier;
import com.enset.metier.SecurityContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@ComponentScan
public class Main {
    public static void main(String[] args) {
        SecurityContext.authenticate("root", "password", new String[] {"USER"});
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        IMetier metier = context.getBean(IMetier.class);

        System.out.println("*****************************");
        System.out.println(metier.getClass().getName());
        System.out.println("*****************************");

        try {
            metier.process();
            System.out.println(metier.compute());
        }catch (RuntimeException e)  {
            System.out.println(e.getMessage());
        }

    }
}