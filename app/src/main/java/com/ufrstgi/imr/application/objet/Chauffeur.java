package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Chauffeur {

    private String id_chauffeur; //login
    private String mot_de_passe;
    private float niveau_batterie_terminal;
    private long id_personne;

    public Chauffeur(String id_chauffeur, String mot_de_passe, float niveau_batterie_terminal, long id_personne) {
        this.id_chauffeur = id_chauffeur;
        this.mot_de_passe = mot_de_passe;
        this.niveau_batterie_terminal = niveau_batterie_terminal;
        this.id_personne = id_personne;
    }

    public String getId_chauffeur() {
        return id_chauffeur;
    }

    public void setId_chauffeur(String id_chauffeur) {
        this.id_chauffeur = id_chauffeur;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public float getNiveau_batterie_terminal() {
        return niveau_batterie_terminal;
    }

    public void setNiveau_batterie_terminal(float niveau_batterie_terminal) {
        this.niveau_batterie_terminal = niveau_batterie_terminal;
    }

    public long getId_personne() {
        return id_personne;
    }

    public void setId_personne(long id_personne) {
        this.id_personne = id_personne;
    }
}
