package com.ufrstgi.imr.application.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas Westermann on 07/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Niveau {

    @SerializedName("id_niveau")
    private int id_niveau;
    @SerializedName("libelle_niveau")
    private String libelle_niveau;
    @SerializedName("prix")
    private float prix;

    public Niveau(int id_niveau, String libelle_niveau, float prix) {
        this.id_niveau = id_niveau;
        this. libelle_niveau = libelle_niveau;
        this.prix = prix;
    }

    public int getId_niveau() {
        return id_niveau;
    }

    public void setId_niveau(int id_niveau) {
        this.id_niveau = id_niveau;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getLibelle_niveau() {
        return libelle_niveau;
    }

    public void setLibelle_niveau(String libelle_niveau) {
        this.libelle_niveau = libelle_niveau;
    }

    @Override
    public String toString() {
        return "Niveau{" + "\n" +
                "\tid_niveau=" + id_niveau + "\n" +
                "\tlibelle_niveau='" + libelle_niveau + '\'' + "\n" +
                "\tprix=" + prix + "\n" +
                "}\n";
    }
}
