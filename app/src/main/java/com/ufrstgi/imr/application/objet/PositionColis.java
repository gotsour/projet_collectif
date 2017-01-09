package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class PositionColis {

    private long id_position_colis;
    private float latitude_colis;
    private float longitude_colis;
    private String date_heure_colis;
    private long id_colis;

    public PositionColis(long id_position_colis, float latitude_colis, float longitude_colis, String date_heure_colis, long id_colis) {
        this.id_position_colis = id_position_colis;
        this.latitude_colis = latitude_colis;
        this.longitude_colis = longitude_colis;
        this.date_heure_colis = date_heure_colis;
        this.id_colis = id_colis;
    }

    public long getId_position_colis() {
        return id_position_colis;
    }

    public void setId_position_colis(long id_position_colis) {
        this.id_position_colis = id_position_colis;
    }

    public float getLatitude_colis() {
        return latitude_colis;
    }

    public void setLatitude_colis(float latitude_colis) {
        this.latitude_colis = latitude_colis;
    }

    public float getLongitude_colis() {
        return longitude_colis;
    }

    public void setLongitude_colis(float longitude_colis) {
        this.longitude_colis = longitude_colis;
    }

    public String getDate_heure_colis() {
        return date_heure_colis;
    }

    public void setDate_heure_colis(String date_heure_colis) {
        this.date_heure_colis = date_heure_colis;
    }

    public long getId_colis() {
        return id_colis;
    }

    public void setId_colis(long id_colis) {
        this.id_colis = id_colis;
    }
}
