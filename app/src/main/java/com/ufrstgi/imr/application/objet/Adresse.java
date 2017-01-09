package com.ufrstgi.imr.application.objet;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Adresse {

    private long id_adresse;
    private String rue;
    private String batiment;
    private String quai;
    private int code_postal;
    private String ville;
    private String pays;

    public Adresse(long id_adresse, String rue, String batiment, String quai, int code_postal, String ville, String pays) {
        this.id_adresse = id_adresse;
        this.rue = rue;
        this.batiment = batiment;
        this.quai = quai;
        this.code_postal = code_postal;
        this.ville = ville;
        this.pays = pays;
    }

    public long getId_adresse() {
        return id_adresse;
    }

    public void setId_adresse(long id_adresse) {
        this.id_adresse = id_adresse;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getBatiment() {
        return batiment;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public String getQuai() {
        return quai;
    }

    public void setQuai(String quai) {
        this.quai = quai;
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
}
