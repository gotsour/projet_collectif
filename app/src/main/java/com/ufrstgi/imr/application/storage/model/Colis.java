package com.ufrstgi.imr.application.storage.model;

import android.provider.ContactsContract;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.ufrstgi.imr.application.storage.DatabaseColis;

/**
 * Created by Duduf on 23/12/2016.
 */

@Table(database = DatabaseColis.class)
public class Colis extends BaseModel {
    @Column
    @PrimaryKey(autoincrement=true)
    int id;

    @Column
    String poids;

    @Column
    String volume;

    @Column
    String niveauBatteriz;

    @Column
    String temperature;

    @Column
    String capaciteChoc;

    @Column
    String niveauBatteri;

    @Column
    @ForeignKey
    Niveau niveau;


    @Override
    public String toString() {
        return "Colis{" +
                "capaciteChoc='" + capaciteChoc + '\'' +
                ", id=" + id +
                ", poids='" + poids + '\'' +
                ", volume='" + volume + '\'' +
                ", niveauBatteriz='" + niveauBatteriz + '\'' +
                ", temperature='" + temperature + '\'' +
                ", niveauBatteri='" + niveauBatteri + '\'' +
                ", niveau=" + niveau +
                '}';
    }

    public String getCapaciteChoc() {
        return capaciteChoc;
    }

    public void setCapaciteChoc(String capaciteChoc) {
        this.capaciteChoc = capaciteChoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public String getNiveauBatteri() {
        return niveauBatteri;
    }

    public void setNiveauBatteri(String niveauBatteri) {
        this.niveauBatteri = niveauBatteri;
    }

    public String getNiveauBatteriz() {
        return niveauBatteriz;
    }

    public void setNiveauBatteriz(String niveauBatteriz) {
        this.niveauBatteriz = niveauBatteriz;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPoids() {
        return poids;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
