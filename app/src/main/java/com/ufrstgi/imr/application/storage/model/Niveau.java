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
public class Niveau extends BaseModel {
    @Column
    @PrimaryKey(autoincrement=true)
    int id;

    @Column
    String nom;

    @Column
    double prix;

    @Column
    boolean sync=false;

    @Override
    public String toString() {
        return "Niveau{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", sync=" + sync +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }
}
