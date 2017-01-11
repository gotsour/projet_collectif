package com.ufrstgi.imr.application.object;

import java.util.Date;

/**
 * Created by Thomas Westermann on 11/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Livraison extends Operation {

    private String nom_operation = "Livraison";

    public Livraison(int id_operation, Date date_theorique, Date date_reelle, Date date_limite, String quai, String batiment, Adresse adresse, Client client) {
        super(id_operation, date_theorique, date_reelle, date_limite, quai, batiment, adresse, client);
    }

    @Override
    public String toString() {
        return nom_operation + "{" + super.toString() + '\'' +
                '}';
    }
}
