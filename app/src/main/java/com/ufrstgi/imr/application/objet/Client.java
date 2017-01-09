package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Client {

    private long id_client;
    private String nom_client;
    private String telephone_client;
    private long id_adresse;
    private long id_personne;

    public Client(long id_client, String nom_client, String telephone_client, long id_adresse, long id_personne) {
        this.id_client = id_client;
        this.nom_client = nom_client;
        this.telephone_client = telephone_client;
        this.id_adresse = id_adresse;
        this.id_personne = id_personne;
    }

    public long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
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

    public long getId_adresse() {
        return id_adresse;
    }

    public void setId_adresse(long id_adresse) {
        this.id_adresse = id_adresse;
    }

    public long getId_personne() {
        return id_personne;
    }

    public void setId_personne(long id_personne) {
        this.id_personne = id_personne;
    }
}
