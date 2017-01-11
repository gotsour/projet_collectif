package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.object.Latlng;

/**
 * Created by Thomas Westermann on 09/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class LatlngManager {

    private static final String TABLE_NAME = "latlng";
    public static final String KEY_ID_LATLNG = "id_latlng";
    public static final String KEY_LATITUDE= "latitude";
    public static final String KEY_LONGITUDE= "longitude";
    public static final String CREATE_TABLE_LATLNG =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_LATLNG+" INTEGER primary key," +
                    " "+KEY_LATITUDE+" REAL," +
                    " "+KEY_LONGITUDE+" REAL" +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public LatlngManager(Context context) {
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

    public long addLatlng(Latlng latlng) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_LATITUDE, latlng.getLatitude());
        values.put(KEY_LONGITUDE, latlng.getLongitude());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateLatlng(Latlng latlng) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_LATITUDE, latlng.getLatitude());
        values.put(KEY_LONGITUDE, latlng.getLongitude());

        String where = KEY_ID_LATLNG+" = ?";
        String[] whereArgs = {latlng.getId_latlng()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteLatlng(Latlng latlng) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_LATLNG+" = ?";
        String[] whereArgs = {latlng.getId_latlng()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Latlng getLatlng(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Latlng l=new Latlng(0,0,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_LATLNG+"="+id, null);
        if (c.moveToFirst()) {
            l.setId_latlng(c.getInt(c.getColumnIndex(KEY_ID_LATLNG)));
            l.setLatitude(c.getFloat(c.getColumnIndex(KEY_LATITUDE)));
            l.setLongitude(c.getFloat(c.getColumnIndex(KEY_LONGITUDE)));

            c.close();
        }

        return l;
    }

    public Cursor getAllLatlng() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
