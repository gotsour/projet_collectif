package com.ufrstgi.imr.application.object;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class PositionChauffeur {

    private int id_position_chauffeur;
    private String date_heure_chauffeur;
    private Chauffeur chauffeur;
    private Latlng latlng;

    public PositionChauffeur(int id_position_chauffeur, String date_heure_chauffeur, Chauffeur chauffeur, Latlng latlng) {
        this.id_position_chauffeur = id_position_chauffeur;
        this.date_heure_chauffeur = date_heure_chauffeur;
        this.chauffeur = chauffeur;
        this.latlng = latlng;
    }

    public int getId_position_chauffeur() {
        return id_position_chauffeur;
    }

    public void setId_position_chauffeur(int id_position_chauffeur) {
        this.id_position_chauffeur = id_position_chauffeur;
    }

    public String getDate_heure_chauffeur() {
        return date_heure_chauffeur;
    }

    public void setDate_heure_chauffeur(String date_heure_chauffeur) {
        this.date_heure_chauffeur = date_heure_chauffeur;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public Latlng getLatlng() {
        return latlng;
    }

    public void setLatlng(Latlng latlng) {
        this.latlng = latlng;
    }

    @Override
    public String toString() {
        return "PositionChauffeur{" +
                "id_position_chauffeur=" + id_position_chauffeur +
                ", date_heure_chauffeur='" + date_heure_chauffeur + '\'' +
                ", chauffeur=" + chauffeur +
                ", latlng=" + latlng +
                '}';
    }
}
