package com.ufrstgi.imr.application.object;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas Westermann on 07/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Colis {

    @SerializedName("id_colis")
    private int id_colis;
    @SerializedName("adresse_mac")
    private String adresse_mac;
    @SerializedName("poids_colis")
    private float poids_colis;
    @SerializedName("volume_colis")
    private float volume_colis;
    @SerializedName("niveau_batterie_colis")
    private float niveau_batterie_colis;
    @SerializedName("temperature_colis")
    private float temperature_colis;
    @SerializedName("capacite_choc_colis")
    private float capacite_choc_colis;

    /* Clefs étrangères */
    @SerializedName("id_niveau")
    private Niveau niveau;
    @SerializedName("id_livraison")
    private Operation livraison;
    @SerializedName("id_reception")
    private Operation reception;
   // @SerializedName("id_tournee")
    private  Tournee tournee;
    @SerializedName("id_client")
    private Client client;

    private Operation currentOperation;

    public Colis(int id_colis, String adresse_mac, float poids_colis, float volume_colis, float niveau_batterie_colis, float temperature_colis, float capacite_choc_colis, Niveau niveau, Operation livraison, Operation reception, Tournee tournee, Client client) {
        this.id_colis = id_colis;
        this.adresse_mac = adresse_mac;
        this.poids_colis = poids_colis;
        this.volume_colis = volume_colis;
        this.niveau_batterie_colis = niveau_batterie_colis;
        this.temperature_colis = temperature_colis;
        this.capacite_choc_colis = capacite_choc_colis;
        this.niveau = niveau;
        this.livraison = livraison;
        this.reception = reception;
        this.tournee = tournee;
        this.client = client;
    }

    public Colis() {
    }

    @Override
    public String toString() {
        return "Colis{" +
                "id_colis=" + id_colis +
                ", adresse_mac='" + adresse_mac + '\'' +
                ", poids_colis=" + poids_colis +
                ", volume_colis=" + volume_colis +
                ", niveau_batterie_colis=" + niveau_batterie_colis +
                ", temperature_colis=" + temperature_colis +
                ", capacite_choc_colis=" + capacite_choc_colis +
                ", niveau=" + niveau +
                ", livraison=" + livraison +
                ", reception=" + reception +
                ", tournee=" + tournee +
                ", client=" + client +
                '}';
    }

    public Operation getCurrentOperation(){
        //recherche prochaine opération
        //todo voir si ok avec test sur data
        if(reception.getDate_reelle()== null && livraison.getDate_theorique().after(reception.getDate_theorique())){
            Log.d("resultat", "current operation reception : ");//+reception.toString());
            return reception;
        }else{
            Log.d("resultat", "current operation livraison : ");//+livraison.toString());
            return livraison;
        }


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

    public String getAdresse_mac() {
        return adresse_mac;
    }

    public void setAdresse_mac(String adresse_mac) {
        this.adresse_mac = adresse_mac;
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

    public Operation getLivraison() {
        return livraison;
    }

    public void setLivraison(Operation livraison) {
        this.livraison = livraison;
    }

    public Operation getReception() {
        return reception;
    }

    public void setReception(Operation reception) {
        this.reception = reception;
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
}
