package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 07/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Colis {

    private long id_colis;
    private float poids_colis;
    private float volume_colis;
    private float niveau_batterie_colis;
    private float temperature_colis;
    private float capacite_choc_colis;

    /* Clefs étrangères */
    private long id_niveau;
    private long id_operation;
    private long id_tournee;
    private long id_client;

    public Colis(long id_colis, float poids_colis, float volume_colis, float niveau_batterie_colis, float temperature_colis, float capacite_choc_colis, long id_niveau, long id_operation, long id_tournee, long id_client) {
        this.id_colis = id_colis;
        this.poids_colis = poids_colis;
        this.volume_colis = volume_colis;
        this.niveau_batterie_colis = niveau_batterie_colis;
        this.temperature_colis = temperature_colis;
        this.capacite_choc_colis = capacite_choc_colis;
        this.id_niveau = id_niveau;
        this.id_operation = id_operation;
        this.id_tournee = id_tournee;
        this.id_client = id_client;
    }

    public long getId_colis() {
        return id_colis;
    }

    public void setId_colis(long id_colis) {
        this.id_colis = id_colis;
    }

    public float getPoids_colis() {
        return poids_colis;
    }

    public void setPoids_colis(float poids_colis) {
        this.poids_colis = poids_colis;
    }

    public float getVolume_colis() {
        return volume_colis;
    }

    public void setVolume_colis(float volume_colis) {
        this.volume_colis = volume_colis;
    }

    public float getNiveau_batterie_colis() {
        return niveau_batterie_colis;
    }

    public void setNiveau_batterie_colis(float niveau_batterie_colis) {
        this.niveau_batterie_colis = niveau_batterie_colis;
    }

    public float getTemperature_colis() {
        return temperature_colis;
    }

    public void setTemperature_colis(float temperature_colis) {
        this.temperature_colis = temperature_colis;
    }

    public float getCapacite_choc_colis() {
        return capacite_choc_colis;
    }

    public void setCapacite_choc_colis(float capacite_choc_colis) {
        this.capacite_choc_colis = capacite_choc_colis;
    }

    public long getId_niveau() {
        return id_niveau;
    }

    public void setId_niveau(long id_niveau) {
        this.id_niveau = id_niveau;
    }

    public long getId_operation() {
        return id_operation;
    }

    public void setId_operation(long id_operation) {
        this.id_operation = id_operation;
    }

    public long getId_tournee() {
        return id_tournee;
    }

    public void setId_tournee(long id_tournee) {
        this.id_tournee = id_tournee;
    }

    public long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
        this.id_client = id_client;
    }
}
