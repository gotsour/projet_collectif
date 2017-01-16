package com.ufrstgi.imr.application.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Tournee {

    @SerializedName("id_tournee")
    private int id_tournee;
    @SerializedName("id_chauffeur")
    private Chauffeur chauffeur;
    @SerializedName("id_camion")
    private Camion camion;

    public Tournee(int id_tournee, Chauffeur chauffeur, Camion camion) {
        this.id_tournee = id_tournee;
        this.chauffeur = chauffeur;
        this.camion = camion;
    }

    public int getId_tournee() {
        return id_tournee;
    }

    public void setId_tournee(int id_tournee) {
        this.id_tournee = id_tournee;
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

    @Override
    public String toString() {
        return "Tournee{" + "\n" +
                "\tid_tournee=" + id_tournee + "\n" +
                "\tchauffeur=" + chauffeur + "\n" +
                "\tcamion=" + camion + "\n" +
                "}\n";
    }
}
