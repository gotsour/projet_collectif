package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 09/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Latlng {

    private int id_latlng;
    private float latitude;
    private float longitude;

    public Latlng(int id_latlng, float latitude, float longitude) {
        this.id_latlng = id_latlng;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId_latlng() {
        return id_latlng;
    }

    public void setId_latlng(int id_latlng) {
        this.id_latlng = id_latlng;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Latlng{" +
                "id_latlng=" + id_latlng +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
