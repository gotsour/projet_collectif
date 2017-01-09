package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Client {

    private int id_client;
    private String nom_client;
    private String telephone_client;
    private Adresse adresse;
    private Personne personne;

    public Client(int id_client, String nom_client, String telephone_client, Adresse adresse, Personne personne) {
        this.id_client = id_client;
        this.nom_client = nom_client;
        this.telephone_client = telephone_client;
        this.adresse = adresse;
        this.personne = personne;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getTelephone_client() {
        return telephone_client;
    }

    public void setTelephone_client(String telephone_client) {
        this.telephone_client = telephone_client;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
}
