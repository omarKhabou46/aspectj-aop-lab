package com.enset;

import com.enset.metier.IMetier;
import com.enset.metier.MetierImpl;
import com.enset.models.Compte;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
       start();
    }

    private static void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("entrer le code du compte : ");
        long code = scanner.nextLong();
        System.out.print("entrer le solde du compte : ");
        double solde = scanner.nextDouble();
        Compte compte = new Compte(code, solde);
        IMetier metier = new MetierImpl();
        metier.addAccount(code, compte);
        while (true) {
            System.out.print("type d'operation : ");
            String type = scanner.next();
            System.out.print("montant : ");
            double montant = scanner.nextDouble();
            if (type.equals("v")) {
                metier.verser(code, montant);
            }else if (type.equals("r")) {
                metier.retirer(code, montant);
            }
            System.out.println(" ----------------- account ------------------");
            System.out.println(metier.getCompte(code));
        }
    }
}