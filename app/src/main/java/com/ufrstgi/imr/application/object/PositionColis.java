package com.ufrstgi.imr.application.object;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class PositionColis {

    @SerializedName("id_position_colis")
    private int id_position_colis;
    @SerializedName("date_heure_colis")
    private Date date_heure_colis;
    @SerializedName("id_colis")
    private int idColis;
    private Colis colis;
    @SerializedName("id_latlng")
    private Latlng latlng;

    public PositionColis(int id_position_colis, Date date_heure_colis, Colis colis, Latlng latlng) {
        this.id_position_colis = id_position_colis;
        this.date_heure_colis = date_heure_colis;
        this.colis = colis;
        this.latlng = latlng;
    }

    public int getIdColis() {
        return idColis;
    }

    public void setIdColis(int idColis) {
        this.idColis = idColis;
    }

    public int getId_position_colis() {
        return id_position_colis;
    }

    public void setId_position_colis(int id_position_colis) {
        this.id_position_colis = id_position_colis;
    }

    public Date getDate_heure_colis() {
        return date_heure_colis;
    }

    public void setDate_heure_colis(Date date_heure_colis) {
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
        return "PositionColis{" + "\n" +
                "\tid_position_colis=" + id_position_colis + "\n" +
                "\tdate_heure_colis='" + date_heure_colis + '\'' + "\n" +
                "\tcolis=" + colis + "\n" +
                "\tlatlng=" + latlng + "\n" +
                "}\n";
    }
}
