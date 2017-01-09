package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class PositionColis {

    private int id_position_colis;
    private String date_heure_colis;
    private int id_colis;
    private int id_latlng;

    public PositionColis(int id_position_colis, String date_heure_colis, int id_colis, int id_latlng) {
        this.id_position_colis = id_position_colis;
        this.date_heure_colis = date_heure_colis;
        this.id_colis = id_colis;
        this.id_latlng = id_latlng;
    }

    public int getId_position_colis() {
        return id_position_colis;
    }

    public void setId_position_colis(int id_position_colis) {
        this.id_position_colis = id_position_colis;
    }

    public String getDate_heure_colis() {
        return date_heure_colis;
    }

    public void setDate_heure_colis(String date_heure_colis) {
        this.date_heure_colis = date_heure_colis;
    }

    public int getId_colis() {
        return id_colis;
    }

    public void setId_colis(int id_colis) {
        this.id_colis = id_colis;
    }

    public int getId_latlng() {
        return id_latlng;
    }

    public void setId_latlng(int id_latlng) {
        this.id_latlng = id_latlng;
    }
}
