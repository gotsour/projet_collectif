package com.ufrstgi.imr.application.object;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Adresse {

    @SerializedName("id_adresse")
    private int id_adresse;
    @SerializedName("rue")
    private String rue;
    @SerializedName("code_postal")
    private int code_postal;
    @SerializedName("ville")
    private String ville;
    @SerializedName("pays")
    private String pays;
    @SerializedName("id_latlng")
    private Latlng latlng;

    public Adresse(int id_adresse, String rue, int code_postal, String ville, String pays, Latlng latlng) {
        this.id_adresse = id_adresse;
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
        this.pays = pays;
        this.latlng = latlng;
    }

    public int getId_adresse() {
        return id_adresse;
    }

    public void setId_adresse(int id_adresse) {
        this.id_adresse = id_adresse;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(int code_postal) {
        this.code_postal = code_postal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Latlng getLatlng() {
        return latlng;
    }

    public void setLatlng(Latlng latlng) {
        this.latlng = latlng;
    }

    @Override
    public String toString() {
        return "Adresse{" + "\n" +
                "\tid_adresse=" + id_adresse + "\n" +
                "\true='" + rue + '\'' + "\n" +
                "\tcode_postal=" + code_postal + "\n" +
                "\tville='" + ville + '\'' + "\n" +
                "\tpays='" + pays + '\'' + "\n" +
                "\tlatlng=" + latlng + "\n" +
                "}\n";
    }
}
