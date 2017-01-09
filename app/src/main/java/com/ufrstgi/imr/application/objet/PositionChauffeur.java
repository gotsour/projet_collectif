package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class PositionChauffeur {

    private int id_position_chauffeur;
    private String date_heure_chauffeur;
    private String id_chauffeur;
    private int id_latlng;

    public PositionChauffeur(int id_position_chauffeur, String date_heure_chauffeur, String id_chauffeur, int id_latlng) {
        this.id_position_chauffeur = id_position_chauffeur;
        this.date_heure_chauffeur = date_heure_chauffeur;
        this.id_chauffeur = id_chauffeur;
        this.id_latlng = id_latlng;
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

    public String getId_chauffeur() {
        return id_chauffeur;
    }

    public void setId_chauffeur(String id_chauffeur) {
        this.id_chauffeur = id_chauffeur;
    }

    public int getId_latlng() {
        return id_latlng;
    }

    public void setId_latlng(int id_latlng) {
        this.id_latlng = id_latlng;
    }
}
