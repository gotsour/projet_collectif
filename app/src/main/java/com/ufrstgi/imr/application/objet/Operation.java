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
    private int id_adresse;
    private int id_client;

    public Operation(int id_operation, String date_theorique, String date_reelle, String date_limite, int estLivraison, String quai, String batiment, int id_adresse, int id_client) {
        this.id_operation = id_operation;
        this.date_theorique = date_theorique;
        this.date_reelle = date_reelle;
        this.date_limite = date_limite;
        this.estLivraison = estLivraison;
        this.quai = quai;
        this.batiment = batiment;
        this.id_adresse = id_adresse;
        this.id_client = id_client;
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

    public int getId_adresse() {
        return id_adresse;
    }

    public void setId_adresse(int id_adresse) {
        this.id_adresse = id_adresse;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
}
