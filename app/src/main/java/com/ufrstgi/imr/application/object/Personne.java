package com.ufrstgi.imr.application.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Personne {

    @SerializedName("id_personne")
    private int id_personne;
    @SerializedName("nom_personne")
    private String nom_personne;
    @SerializedName("prenom_personne")
    private String prenom_personne;
    @SerializedName("telephone_personne")
    private String telephone_personne;

    public Personne(int id_personne, String nom_personne, String prenom_personne, String telephone_personne) {
        this.id_personne = id_personne;
        this.nom_personne = nom_personne;
        this.prenom_personne = prenom_personne;
        this.telephone_personne = telephone_personne;
    }

    public int getId_personne() {
        return id_personne;
    }

    public void setId_personne(int id_personne) {
        this.id_personne = id_personne;
    }

    public String getNom_personne() {
        return nom_personne;
    }

    public void setNom_personne(String nom_personne) {
        this.nom_personne = nom_personne;
    }

    public String getPrenom_personne() {
        return prenom_personne;
    }

    public void setPrenom_personne(String prenom_personne) {
        this.prenom_personne = prenom_personne;
    }

    public String getTelephone_personne() {
        return telephone_personne;
    }

    public void setTelephone_personne(String telephone_personne) {
        this.telephone_personne = telephone_personne;
    }

    @Override
    public String toString() {
        return "Personne{" + "\n" +
                "\tid_personne=" + id_personne + "\n" +
                "\tnom_personne='" + nom_personne + '\'' + "\n" +
                "\tprenom_personne='" + prenom_personne + '\'' + "\n" +
                "\ttelephone_personne='" + telephone_personne + '\'' + "\n" +
                "}\n";
    }
}
