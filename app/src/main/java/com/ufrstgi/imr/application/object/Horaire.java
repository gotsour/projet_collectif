package com.ufrstgi.imr.application.object;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Horaire {

    @SerializedName("id_horaire")
    private int id_horaire;
    @SerializedName("heure_debut")
    private Date heure_debut;
    @SerializedName("heure_fin")
    private Date heure_fin;
    @SerializedName("id_chauffeur")
    private Chauffeur chauffeur;

    public Horaire(int id_horaire, Date heure_fin, Date heure_debut, Chauffeur chauffeur) {
        this.id_horaire = id_horaire;
        this.heure_fin = heure_fin;
        this.heure_debut = heure_debut;
        this.chauffeur = chauffeur;
    }

    public int getId_horaire() {
        return id_horaire;
    }

    public void setId_horaire(int id_horaire) {
        this.id_horaire = id_horaire;
    }

    public Date getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(Date heure_debut) {
        this.heure_debut = heure_debut;
    }

    public Date getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(Date heure_fin) {
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
