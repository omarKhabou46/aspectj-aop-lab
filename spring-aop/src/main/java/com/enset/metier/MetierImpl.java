package com.enset.metier;

import com.enset.annotations.Log;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MetierImpl implements IMetier {


    @Override
    public void process() {
        System.out.println("Business Process ....");
    }

    @Log
    @Override
    public double compute() {
        double data = 72.5;

        System.out.println("Business Computing and Returning ....");
        return data;
    }
}
