package com.enset.metier;

import com.enset.annotations.Log;
import com.enset.annotations.SecuredByAspect;
import org.springframework.stereotype.Service;

@Service
public class MetierImpl implements IMetier {


    @SecuredByAspect(roles={"USER", "ADMIN"})
    @Override
    public void process() {
        System.out.println("Business Process ....");
    }

    @SecuredByAspect(roles={"ADMIN"})
    @Log
    @Override
    public double compute() {
        double data = 72.5;
        System.out.println("Business Computing and Returning ....");
        return data;
    }
}
