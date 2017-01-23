package com.ufrstgi.imr.application.object;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Tournee {

    @SerializedName("id_tournee")
    private int id_tournee;
    @SerializedName("date_debut")
    private Date date_debut;
    @SerializedName("id_chauffeur")
    private Chauffeur chauffeur;
    @SerializedName("id_camion")
    private Camion camion;

    public Tournee(int id_tournee, Date date_debut, Chauffeur chauffeur, Camion camion) {
        this.id_tournee = id_tournee;
        this.date_debut = date_debut;
        this.chauffeur = chauffeur;
        this.camion = camion;
    }

    @Override
    public String toString() {
        return "Tournee{" +
                "id_tournee=" + id_tournee +
                ", date_debut=" + date_debut +
                ", chauffeur=" + chauffeur +
                ", camion=" + camion +
                '}';
    }

    public int getId_tournee() {
        return id_tournee;
    }

    public void setId_tournee(int id_tournee) {
        this.id_tournee = id_tournee;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }
}
