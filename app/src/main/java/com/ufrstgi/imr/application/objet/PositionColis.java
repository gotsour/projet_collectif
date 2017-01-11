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
    private Colis colis;
    private Latlng latlng;

    public PositionColis(int id_position_colis, String date_heure_colis, Colis colis, Latlng latlng) {
        this.id_position_colis = id_position_colis;
        this.date_heure_colis = date_heure_colis;
        this.colis = colis;
        this.latlng = latlng;
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

    public Colis getColis() {
        return colis;
    }

    public void setColis(Colis colis) {
        this.colis = colis;
    }

    public Latlng getLatlng() {
        return latlng;
    }

    public void setLatlng(Latlng latlng) {
        this.latlng = latlng;
    }

    @Override
    public String toString() {
        return "PositionColis{" +
                "id_position_colis=" + id_position_colis +
                ", date_heure_colis='" + date_heure_colis + '\'' +
                ", colis=" + colis +
                ", latlng=" + latlng +
                '}';
    }
}
