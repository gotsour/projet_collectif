package com.ufrstgi.imr.application.object;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Horaire {

    private int id_horaire;
    private String heure_debut;
    private String heure_fin;
    private Chauffeur chauffeur;

    public Horaire(int id_horaire, String heure_debut, String heure_fin, Chauffeur chauffeur) {
        this.id_horaire = id_horaire;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.chauffeur = chauffeur;
    }

    public int getId_horaire() {
        return id_horaire;
    }

    public void setId_horaire(int id_horaire) {
        this.id_horaire = id_horaire;
    }

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }

    public String getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(String heure_fin) {
        this.heure_fin = heure_fin;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    @Override
    public String toString() {
        return "Horaire{" +
                "id_horaire=" + id_horaire +
                ", heure_debut='" + heure_debut + '\'' +
                ", heure_fin='" + heure_fin + '\'' +
                ", chauffeur=" + chauffeur +
                '}';
    }
}
