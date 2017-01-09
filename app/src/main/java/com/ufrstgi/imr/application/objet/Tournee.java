package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Tournee {

    private int id_tournee;
    private String id_chauffeur;
    private String id_camion;

    public Tournee(int id_tournee, String id_chauffeur, String id_camion) {
        this.id_tournee = id_tournee;
        this.id_chauffeur = id_chauffeur;
        this.id_camion = id_camion;
    }

    public int getId_tournee() {
        return id_tournee;
    }

    public void setId_tournee(int id_tournee) {
        this.id_tournee = id_tournee;
    }

    public String getId_chauffeur() {
        return id_chauffeur;
    }

    public void setId_chauffeur(String id_chauffeur) {
        this.id_chauffeur = id_chauffeur;
    }

    public String getId_camion() {
        return id_camion;
    }

    public void setId_camion(String id_camion) {
        this.id_camion = id_camion;
    }
}
