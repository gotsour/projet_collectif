package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class PositionChauffeur {

    private long id_position_chauffeur;
    private float latitude_chauffeur;
    private float longitude_chauffeur;
    private String date_heure_chauffeur;
    private String id_chauffeur;

    public PositionChauffeur(long id_position_chauffeur, float latitude_chauffeur, float longitude_chauffeur, String date_heure_chauffeur, String id_chauffeur) {
        this.id_position_chauffeur = id_position_chauffeur;
        this.latitude_chauffeur = latitude_chauffeur;
        this.longitude_chauffeur = longitude_chauffeur;
        this.date_heure_chauffeur = date_heure_chauffeur;
        this.id_chauffeur = id_chauffeur;
    }

    public long getId_position_chauffeur() {
        return id_position_chauffeur;
    }

    public void setId_position_chauffeur(long id_position_chauffeur) {
        this.id_position_chauffeur = id_position_chauffeur;
    }

    public float getLatitude_chauffeur() {
        return latitude_chauffeur;
    }

    public void setLatitude_chauffeur(float latitude_chauffeur) {
        this.latitude_chauffeur = latitude_chauffeur;
    }

    public float getLongitude_chauffeur() {
        return longitude_chauffeur;
    }

    public void setLongitude_chauffeur(float longitude_chauffeur) {
        this.longitude_chauffeur = longitude_chauffeur;
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
}
