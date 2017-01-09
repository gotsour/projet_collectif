package com.ufrstgi.imr.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.objet.Operation;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class OperationManager {

    private static final String TABLE_NAME = "operation";
    public static final String KEY_ID_OPERATION = "id_operation";
    public static final String KEY_DATE_THEORIQUE = "date_theorique";
    public static final String KEY_DATE_REELLE= "heure_relle_operation";
    public static final String KEY_DATE_LIMITE= "date_limite_operation";
    public static final String KEY_EST_LIVRAISON= "estLivraison";
    public static final String KEY_QUAI= "quai";
    public static final String KEY_BATIMENT= "batiment";
    public static final String KEY_ID_ADRESSE= "id_adresse";
    public static final String KEY_ID_CLIENT= "id_client";

    public static final String CREATE_TABLE_OPERATION =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_OPERATION+" INTEGER primary key," +
                    " "+KEY_DATE_THEORIQUE+" TEXT," +
                    " "+KEY_DATE_REELLE+" TEXT," +
                    " "+KEY_DATE_LIMITE+" TEXT," +
                    " "+KEY_EST_LIVRAISON+" INTEGER," +
                    " "+KEY_QUAI+" TEXT," +
                    " "+KEY_BATIMENT+" TEXT," +
                    " "+KEY_ID_ADRESSE+" INTEGER," +
                    " "+KEY_ID_CLIENT+" INTEGER," +
                    " FOREIGN KEY("+KEY_ID_ADRESSE+") REFERENCES adresse("+KEY_ID_ADRESSE+")," +
                    " FOREIGN KEY("+KEY_ID_CLIENT+") REFERENCES client("+KEY_ID_CLIENT+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public OperationManager(Context context) {
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

    public long addOperation(Operation operation) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_OPERATION, operation.getId_operation());
        values.put(KEY_DATE_THEORIQUE, operation.getDate_theorique());
        values.put(KEY_DATE_REELLE, operation.getDate_reelle());
        values.put(KEY_DATE_LIMITE, operation.getDate_limite());
        values.put(KEY_EST_LIVRAISON, operation.getEstLivraison());
        values.put(KEY_QUAI, operation.getQuai());
        values.put(KEY_BATIMENT, operation.getBatiment());
        values.put(KEY_ID_ADRESSE, operation.getId_adresse());
        values.put(KEY_ID_CLIENT, operation.getId_client());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateOperation(Operation operation) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_OPERATION, operation.getId_operation());
        values.put(KEY_DATE_THEORIQUE, operation.getDate_theorique());
        values.put(KEY_DATE_REELLE, operation.getDate_reelle());
        values.put(KEY_DATE_LIMITE, operation.getDate_limite());
        values.put(KEY_EST_LIVRAISON, operation.getEstLivraison());
        values.put(KEY_QUAI, operation.getQuai());
        values.put(KEY_BATIMENT, operation.getBatiment());
        values.put(KEY_ID_ADRESSE, operation.getId_adresse());
        values.put(KEY_ID_CLIENT, operation.getId_client());

        String where = KEY_ID_OPERATION+" = ?";
        String[] whereArgs = {operation.getId_operation()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteOperation(Operation operation) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_OPERATION+" = ?";
        String[] whereArgs = {operation.getId_operation()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Operation getOperation(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Operation o = new Operation(0,"","","",0,"","",0,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_OPERATION+"="+id, null);
        if (c.moveToFirst()) {
            o.setId_operation(c.getInt(c.getColumnIndex(KEY_ID_OPERATION)));
            o.setDate_theorique(c.getString(c.getColumnIndex(KEY_DATE_THEORIQUE)));
            o.setDate_reelle(c.getString(c.getColumnIndex(KEY_DATE_REELLE)));
            o.setDate_limite(c.getString(c.getColumnIndex(KEY_DATE_LIMITE)));
            o.setEstLivraison(c.getInt(c.getColumnIndex(KEY_EST_LIVRAISON)));
            o.setQuai(c.getString(c.getColumnIndex(KEY_QUAI)));
            o.setBatiment(c.getString(c.getColumnIndex(KEY_BATIMENT)));
            o.setId_adresse(c.getInt(c.getColumnIndex(KEY_ID_ADRESSE)));
            o.setId_client(c.getInt(c.getColumnIndex(KEY_ID_CLIENT)));

            c.close();
        }

        return o;
    }

    public Cursor getAllOperation() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
