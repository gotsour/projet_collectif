package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Operation {

    private long id_operation;
    private String heure_theorique_operation;
    private String heure_reelle_operation;
    private String date_limite_operation;
    private int estLivraison;
    private int estReception;
    private long id_adresse;
    private long id_client;

    public Operation(long id_operation, String heure_theorique_operation, String heure_reelle_operation, String date_limite_operation, int estLivraison, int estReception, long id_adresse, long id_client) {
        this.id_operation = id_operation;
        this.heure_theorique_operation = heure_theorique_operation;
        this.heure_reelle_operation = heure_reelle_operation;
        this.date_limite_operation = date_limite_operation;
        this.estLivraison = estLivraison;
        this.estReception = estReception;
        this.id_adresse = id_adresse;
        this.id_client = id_client;
    }

    public long getId_operation() {
        return id_operation;
    }

    public void setId_operation(long id_operation) {
        this.id_operation = id_operation;
    }

    public String getHeure_theorique_operation() {
        return heure_theorique_operation;
    }

    public void setHeure_theorique_operation(String heure_theorique_operation) {
        this.heure_theorique_operation = heure_theorique_operation;
    }

    public String getHeure_reelle_operation() {
        return heure_reelle_operation;
    }

    public void setHeure_reelle_operation(String heure_reelle_operation) {
        this.heure_reelle_operation = heure_reelle_operation;
    }

    public String getDate_limite_operation() {
        return date_limite_operation;
    }

    public void setDate_limite_operation(String date_limite_operation) {
        this.date_limite_operation = date_limite_operation;
    }

    public int getEstLivraison() {
        return estLivraison;
    }

    public void setEstLivraison(int estLivraison) {
        this.estLivraison = estLivraison;
    }

    public int getEstReception() {
        return estReception;
    }

    public void setEstReception(int estReception) {
        this.estReception = estReception;
    }

    public long getId_adresse() {
        return id_adresse;
    }

    public void setId_adresse(long id_adresse) {
        this.id_adresse = id_adresse;
    }

    public long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
        this.id_client = id_client;
    }
}
