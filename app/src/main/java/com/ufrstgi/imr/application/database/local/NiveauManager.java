package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.object.Niveau;

import java.util.ArrayList;

/**
 * Created by Thomas Westermann on 07/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class NiveauManager {

    public static final String TABLE_NAME = "niveau";
    public static final String KEY_ID_NIVEAU = "id_niveau";
    public static final String KEY_LIBELLE_NIVEAU = "libelle_niveau";
    public static final String KEY_PRIX= "prix";
    public static final String CREATE_TABLE_NIVEAU =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_NIVEAU+" INTEGER primary key," +
                    " "+KEY_LIBELLE_NIVEAU+" TEXT," +
                    " "+KEY_PRIX+" REAL" +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public NiveauManager(Context context) {
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

    public long addNiveau(Niveau niveau) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_NIVEAU, niveau.getId_niveau());
        values.put(KEY_LIBELLE_NIVEAU, niveau.getLibelle_niveau());
        values.put(KEY_PRIX, niveau.getPrix());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateNiveau(Niveau niveau) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_LIBELLE_NIVEAU, niveau.getLibelle_niveau());
        values.put(KEY_PRIX, niveau.getPrix());

        String where = KEY_ID_NIVEAU+" = ?";
        String[] whereArgs = {niveau.getId_niveau()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteNiveau(Niveau niveau) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_NIVEAU+" = ?";
        String[] whereArgs = {niveau.getId_niveau()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Niveau getNiveau(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Niveau n=new Niveau(0,"",0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_NIVEAU+"="+id, null);
        if (c.moveToFirst()) {
            n.setId_niveau(c.getInt(c.getColumnIndex(KEY_ID_NIVEAU)));
            n.setLibelle_niveau(c.getString(c.getColumnIndex(KEY_LIBELLE_NIVEAU)));
            n.setPrix(c.getFloat(c.getColumnIndex(KEY_PRIX)));

            c.close();
        }

        return n;
    }

    public ArrayList<Niveau> getAllNiveau() {
        ArrayList<Niveau> mesNiveau = new ArrayList<Niveau>();
        // sélection de tous les enregistrements de la table
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        Niveau n;

        if (c.moveToFirst()) {
            do {
                n=new Niveau(0,"",0);
                n.setId_niveau(c.getInt(c.getColumnIndex(KEY_ID_NIVEAU)));
                n.setLibelle_niveau(c.getString(c.getColumnIndex(KEY_LIBELLE_NIVEAU)));
                n.setPrix(c.getFloat(c.getColumnIndex(KEY_PRIX)));
                mesNiveau.add(n);
            } while (c.moveToNext());
        }

        c.close();
        return mesNiveau;
    }
}
