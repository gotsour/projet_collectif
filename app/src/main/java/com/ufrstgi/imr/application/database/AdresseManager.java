package com.ufrstgi.imr.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.objet.Adresse;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class AdresseManager {

    private static final String TABLE_NAME = "adresse";
    public static final String KEY_ID_ADRESSE = "id_adresse";
    public static final String KEY_RUE = "rue";
    public static final String KEY_BATIMENT= "batiment";
    public static final String KEY_QUAI= "quai";
    public static final String KEY_CODE_POSTAL= "code_postal";
    public static final String KEY_VILLE= "ville";
    public static final String KEY_PAYS= "pays";

    public static final String CREATE_TABLE_ADRESSE =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_ADRESSE+" INTEGER primary key," +
                    " "+KEY_RUE+" TEXT" +
                    " "+KEY_BATIMENT+" TEXT" +
                    " "+KEY_QUAI+" TEXT" +
                    " "+KEY_CODE_POSTAL+" INTEGER" +
                    " "+KEY_VILLE+" TEXT" +
                    " "+KEY_PAYS+" TEXT" +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public AdresseManager(Context context) {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open() {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addAdresse(Adresse adresse) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_RUE, adresse.getRue());
        values.put(KEY_BATIMENT, adresse.getBatiment());
        values.put(KEY_QUAI, adresse.getQuai());
        values.put(KEY_CODE_POSTAL, adresse.getCode_postal());
        values.put(KEY_VILLE, adresse.getVille());
        values.put(KEY_PAYS, adresse.getPays());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateAdresse(Adresse adresse) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_RUE, adresse.getRue());
        values.put(KEY_BATIMENT, adresse.getBatiment());
        values.put(KEY_QUAI, adresse.getQuai());
        values.put(KEY_CODE_POSTAL, adresse.getCode_postal());
        values.put(KEY_VILLE, adresse.getVille());
        values.put(KEY_PAYS, adresse.getPays());

        String where = KEY_ID_ADRESSE+" = ?";
        String[] whereArgs = {adresse.getId_adresse()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteAdresse(Adresse adresse) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_ADRESSE+" = ?";
        String[] whereArgs = {adresse.getId_adresse()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Adresse getAdresse(long id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Adresse a=new Adresse(0,"","","",0,"","");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_ADRESSE+"="+id, null);
        if (c.moveToFirst()) {
            a.setId_adresse(c.getLong(c.getColumnIndex(KEY_ID_ADRESSE)));
            a.setRue(c.getString(c.getColumnIndex(KEY_RUE)));
            a.setBatiment(c.getString(c.getColumnIndex(KEY_BATIMENT)));
            a.setQuai(c.getString(c.getColumnIndex(KEY_QUAI)));
            a.setCode_postal(c.getInt(c.getColumnIndex(KEY_CODE_POSTAL)));
            a.setVille(c.getString(c.getColumnIndex(KEY_VILLE)));
            a.setPays(c.getString(c.getColumnIndex(KEY_PAYS)));

            c.close();
        }

        return a;
    }

    public Cursor getAllAdresse() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
