package com.enset.metier;

import com.enset.models.Compte;

import java.util.HashMap;
import java.util.Map;

public class MetierImpl implements IMetier {

    private Map<Long, Compte> accounts = new HashMap<>();

    @Override
    public void addAccount(Long code, Compte c) {
        accounts.put(code, c);
    }

    @Override
    public void verser(Long code, double solde) {
        Compte compte = accounts.get(code);
        compte.setBalance(compte.getBalance() + solde);
    }

    @Override
    public void retirer(Long code, double solde) {
        Compte compte = accounts.get(code);
        compte.setBalance(compte.getBalance() - solde);
    }

    @Override
    public Compte getCompte(Long code) {
        return accounts.get(code);
    }
}
