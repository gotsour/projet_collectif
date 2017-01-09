package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Operation {

    private int id_operation;
    private String date_theorique;
    private String date_reelle;
    private String date_limite;
    private int estLivraison;
    private String quai;
    private String batiment;
    private Adresse adresse;
    private Client client;

    public Operation(int id_operation, String date_theorique, String date_reelle, String date_limite, int estLivraison, String quai, String batiment, Adresse adresse, Client client) {
        this.id_operation = id_operation;
        this.date_theorique = date_theorique;
        this.date_reelle = date_reelle;
        this.date_limite = date_limite;
        this.estLivraison = estLivraison;
        this.quai = quai;
        this.batiment = batiment;
        this.adresse = adresse;
        this.client = client;
    }

    public int getId_operation() {
        return id_operation;
    }

    public void setId_operation(int id_operation) {
        this.id_operation = id_operation;
    }

    public String getDate_theorique() {
        return date_theorique;
    }

    public void setDate_theorique(String date_theorique) {
        this.date_theorique = date_theorique;
    }

    public String getDate_reelle() {
        return date_reelle;
    }

    public void setDate_reelle(String date_reelle) {
        this.date_reelle = date_reelle;
    }

    public String getDate_limite() {
        return date_limite;
    }

    public void setDate_limite(String date_limite) {
        this.date_limite = date_limite;
    }

    public int getEstLivraison() {
        return estLivraison;
    }

    public void setEstLivraison(int estLivraison) {
        this.estLivraison = estLivraison;
    }

    public String getQuai() {
        return quai;
    }

    public void setQuai(String quai) {
        this.quai = quai;
    }

    public String getBatiment() {
        return batiment;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
