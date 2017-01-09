package com.ufrstgi.imr.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.objet.PositionColis;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class PositionColisManager {

    private static final String TABLE_NAME = "positionColis";
    public static final String KEY_ID_POSITION_COLIS = "id_position_colis";
    public static final String KEY_LATITUDE_COLIS = "latitude_colis";
    public static final String KEY_LONGITUDE_COLIS = "longitude_colis";
    public static final String KEY_DATE_HEURE_COLIS = "date_heure_colis";
    public static final String KEY_ID_COLIS = "id_colis";

    public static final String CREATE_TABLE_POSITION_COLIS =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_POSITION_COLIS+" INTEGER primary key," +
                    " "+KEY_LATITUDE_COLIS+" REAL" +
                    " "+KEY_LONGITUDE_COLIS+" REAL" +
                    " "+KEY_DATE_HEURE_COLIS+" TEXT" +
                    " "+KEY_ID_COLIS+" INTEGER" +
                    " FOREIGN KEY("+KEY_ID_COLIS+") REFERENCES colis("+KEY_ID_COLIS+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public PositionColisManager(Context context) {
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

    public long addPositionColis(PositionColis positionColis) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_POSITION_COLIS, positionColis.getId_position_colis());
        values.put(KEY_LATITUDE_COLIS, positionColis.getLatitude_colis());
        values.put(KEY_LONGITUDE_COLIS, positionColis.getLongitude_colis());
        values.put(KEY_DATE_HEURE_COLIS, positionColis.getDate_heure_colis());
        values.put(KEY_ID_COLIS, positionColis.getId_colis());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updatePositionColis(PositionColis positionColis) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_POSITION_COLIS, positionColis.getId_position_colis());
        values.put(KEY_LATITUDE_COLIS, positionColis.getLatitude_colis());
        values.put(KEY_LONGITUDE_COLIS, positionColis.getLongitude_colis());
        values.put(KEY_DATE_HEURE_COLIS, positionColis.getDate_heure_colis());
        values.put(KEY_ID_COLIS, positionColis.getId_colis());

        String where = KEY_ID_POSITION_COLIS+" = ?";
        String[] whereArgs = {positionColis.getId_position_colis()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deletePositionColis(PositionColis positionColis) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_POSITION_COLIS+" = ?";
        String[] whereArgs = {positionColis.getId_position_colis()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public PositionColis getPositionColis(Long id) {
        // Retourne le niveau dont l'id est passé en paramètre

        PositionColis p = new PositionColis(0,0,0,"",0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_POSITION_COLIS+"="+id, null);
        if (c.moveToFirst()) {
            p.setId_position_colis(c.getLong(c.getColumnIndex(KEY_ID_POSITION_COLIS)));
            p.setLatitude_colis(c.getFloat(c.getColumnIndex(KEY_LATITUDE_COLIS)));
            p.setLongitude_colis(c.getFloat(c.getColumnIndex(KEY_LONGITUDE_COLIS)));
            p.setDate_heure_colis(c.getString(c.getColumnIndex(KEY_DATE_HEURE_COLIS)));
            p.setId_colis(c.getLong(c.getColumnIndex(KEY_ID_COLIS)));

            c.close();
        }

        return p;
    }

    public Cursor getAllPositionColis() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
