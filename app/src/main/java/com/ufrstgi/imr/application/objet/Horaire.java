package com.ufrstgi.imr.application.objet;

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
    private String id_chauffeur;

    public Horaire(int id_horaire, String heure_debut, String heure_fin, String id_chauffeur) {
        this.id_horaire = id_horaire;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.id_chauffeur = id_chauffeur;
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

    public String getId_chauffeur() {
        return id_chauffeur;
    }

    public void setId_chauffeur(String id_chauffeur) {
        this.id_chauffeur = id_chauffeur;
    }
}
