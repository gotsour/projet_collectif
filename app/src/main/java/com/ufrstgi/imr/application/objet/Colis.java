package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 07/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Colis {

    private int id_colis;
    private float poids_colis;
    private float volume_colis;
    private float niveau_batterie_colis;
    private float temperature_colis;
    private float capacite_choc_colis;

    /* Clefs étrangères */
    private Niveau niveau;
    private Operation operation;
    private  Tournee tournee;
    private Client client;

    public Colis(int id_colis, float poids_colis, float volume_colis, float niveau_batterie_colis, float temperature_colis, float capacite_choc_colis, Niveau niveau, Operation operation, Tournee tournee, Client client) {
        this.id_colis = id_colis;
        this.poids_colis = poids_colis;
        this.volume_colis = volume_colis;
        this.niveau_batterie_colis = niveau_batterie_colis;
        this.temperature_colis = temperature_colis;
        this.capacite_choc_colis = capacite_choc_colis;
        this.niveau = niveau;
        this.operation = operation;
        this.tournee = tournee;
        this.client = client;
    }

    public int getId_colis() {
        return id_colis;
    }

    public void setId_colis(int id_colis) {
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

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Tournee getTournee() {
        return tournee;
    }

    public void setTournee(Tournee tournee) {
        this.tournee = tournee;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Colis{" +
                "id_colis=" + id_colis +
                ", poids_colis=" + poids_colis +
                ", volume_colis=" + volume_colis +
                ", niveau_batterie_colis=" + niveau_batterie_colis +
                ", temperature_colis=" + temperature_colis +
                ", capacite_choc_colis=" + capacite_choc_colis +
                ", niveau=" + niveau +
                ", operation=" + operation +
                ", tournee=" + tournee +
                ", client=" + client +
                '}';
    }
}
