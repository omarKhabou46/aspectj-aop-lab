package com.enset.metier;

import com.enset.models.Compte;

public interface IMetier {

    void addAccount(Long code, Compte c);
    void verser(Long code, double solde);
    void retirer(Long code, double solde);
    Compte getCompte(Long code);
}
