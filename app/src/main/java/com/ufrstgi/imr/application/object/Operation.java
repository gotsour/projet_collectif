package com.ufrstgi.imr.application.object;

import java.util.Date;


import java.util.Date;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public abstract class Operation {


    private int id_operation;
    private Date date_theorique;
    private Date date_reelle;
    private Date date_limite;
    private String quai;
    private String batiment;
    private Adresse adresse;
    private Client client;

    public Operation(int id_operation, Date date_theorique, Date date_reelle, Date date_limite, String quai, String batiment, Adresse adresse, Client client) {
        this.id_operation = id_operation;
        this.date_theorique = date_theorique;
        this.date_reelle = date_reelle;
        this.date_limite = date_limite;
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

    public Date getDate_theorique() {
        return date_theorique;
    }

    public void setDate_theorique(Date date_theorique) {
        this.date_theorique = date_theorique;
    }

    public Date getDate_reelle() {
        return date_reelle;
    }

    public void setDate_reelle(Date date_reelle) {
        this.date_reelle = date_reelle;
    }

    public Date getDate_limite() {
        return date_limite;
    }

    public void setDate_limite(Date date_limite) {
        this.date_limite = date_limite;
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

    @Override
    public String toString() {
        return "\tid_operation=" + id_operation+ "\n" +
                "\tdate_theorique='" + date_theorique + '\'' + "\n" +
                "\tdate_reelle='" + date_reelle + '\'' + "\n" +
                "\tdate_limite='" + date_limite + '\'' + "\n" +
                "\tquai='" + quai + '\'' + "\n" +
                "\tbatiment='" + batiment + '\'' + "\n" +
                "\tadresse=" + adresse + "\n" +
                "\tclient=" + client + "\n";
    }
}
