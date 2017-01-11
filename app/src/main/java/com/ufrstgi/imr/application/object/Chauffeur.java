package com.ufrstgi.imr.application.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Chauffeur {

    @SerializedName("id_chauffeur")
    private String id_chauffeur; //login
    @SerializedName("mot_de_passe")
    private String mot_de_passe;
    @SerializedName("niveau_batterie_terminal")
    private float niveau_batterie_terminal;
    @SerializedName("id_personne")
    private Personne personne;

    public Chauffeur(String id_chauffeur, String mot_de_passe, float niveau_batterie_terminal, Personne personne) {
        this.id_chauffeur = id_chauffeur;
        this.mot_de_passe = mot_de_passe;
        this.niveau_batterie_terminal = niveau_batterie_terminal;
        this.personne = personne;
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

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    @Override
    public String toString() {
        return "Chauffeur{" +
                "id_chauffeur='" + id_chauffeur + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", niveau_batterie_terminal=" + niveau_batterie_terminal +
                ", personne=" + personne +
                '}';
    }
}
