package com.enset.models;

public class Compte {
    private Long code;
    private double balance;


    public Compte(Long code, double balance) {
        this.code = code;
        this.balance = balance;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
