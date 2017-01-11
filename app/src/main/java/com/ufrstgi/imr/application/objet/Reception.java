package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 11/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Reception extends Operation {

    private String nom_operation = "Réception";

    public Reception(int id_operation, String date_theorique, String date_reelle, String date_limite, int estLivraison, String quai, String batiment, Adresse adresse, Client client) {
        super(id_operation, date_theorique, date_reelle, date_limite, estLivraison, quai, batiment, adresse, client);
    }

    @Override
    public String toString() {
        return nom_operation + "{" + super.toString() + '\'' +
                '}';
    }
}
