package com.ufrstgi.imr.application.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Camion {

    @SerializedName("id_camion")
    public String id_camion; // Numéro d'immatriculation
    @SerializedName("nom")
    public String nom_camion; // Marque
    @SerializedName("volume_camion")
    public float volume_camion;
    @SerializedName("taille_camion")
    public float taille_camion;
    @SerializedName("poids_chargement_camion")
    public float poids_chargement_camion;

    public Camion(String id_camion, String nom_camion, float volume_camion, float taille_camion, float poids_chargement_camion) {
        this.poids_chargement_camion = poids_chargement_camion;
        this.id_camion = id_camion;
        this.nom_camion = nom_camion;
        this.volume_camion = volume_camion;
        this.taille_camion = taille_camion;
    }

    public String getId_camion() {
        return id_camion;
    }

    public void setId_camion(String id_camion) {
        this.id_camion = id_camion;
    }

    public String getNom_camion() {
        return nom_camion;
    }

    public void setNom_camion(String nom_camion) {
        this.nom_camion = nom_camion;
    }

    public float getVolume_camion() {
        return volume_camion;
    }

    public void setVolume_camion(float volume_camion) {
        this.volume_camion = volume_camion;
    }

    public float getTaille_camion() {
        return taille_camion;
    }

    public void setTaille_camion(float taille_camion) {
        this.taille_camion = taille_camion;
    }

    public float getPoids_chargement_camion() {
        return poids_chargement_camion;
    }

    public void setPoids_chargement_camion(float poids_chargement_camion) {
        this.poids_chargement_camion = poids_chargement_camion;
    }

    @Override
    public String toString() {
        return "Camion{" + "\n" +
                "\tid_camion='" + id_camion + '\'' + "\n" +
                "\tnom_camion='" + nom_camion + '\'' + "\n" +
                "\tvolume_camion=" + volume_camion + "\n" +
                "\ttaille_camion=" + taille_camion + "\n" +
                "\tpoids_chargement_camion=" + poids_chargement_camion + "\n" +
                "}\n";
    }
}
