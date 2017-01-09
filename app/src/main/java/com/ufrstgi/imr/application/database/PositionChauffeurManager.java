package com.ufrstgi.imr.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.objet.PositionChauffeur;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class PositionChauffeurManager {

    private static final String TABLE_NAME = "positionChauffeur";
    public static final String KEY_ID_POSITION_CHAUFFEUR = "id_position_chauffeur";
    public static final String KEY_LATITUDE_CHAUFFEUR = "latitude_chauffeur";
    public static final String KEY_LONGITUDE_CHAUFFEUR = "longitude_chauffeur";
    public static final String KEY_DATE_HEURE_CHAUFFEUR= "date_heure_chauffeur";
    public static final String KEY_ID_CHAUFFEUR= "id_chauffeur";

    public static final String CREATE_TABLE_POSITION_CHAUFFEUR =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_POSITION_CHAUFFEUR+" INTEGER primary key," +
                    " "+KEY_LATITUDE_CHAUFFEUR+" REAL" +
                    " "+KEY_LONGITUDE_CHAUFFEUR+" REAL" +
                    " "+KEY_DATE_HEURE_CHAUFFEUR+" TEXT" +
                    " "+KEY_ID_CHAUFFEUR+" TEXT" +
                    " FOREIGN KEY("+KEY_ID_CHAUFFEUR+") REFERENCES chauffeur("+KEY_ID_CHAUFFEUR+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public PositionChauffeurManager(Context context) {
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

    public long addPositionChauffeur(PositionChauffeur positionChauffeur) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_POSITION_CHAUFFEUR, positionChauffeur.getId_position_chauffeur());
        values.put(KEY_LATITUDE_CHAUFFEUR, positionChauffeur.getLatitude_chauffeur());
        values.put(KEY_LONGITUDE_CHAUFFEUR, positionChauffeur.getLongitude_chauffeur());
        values.put(KEY_DATE_HEURE_CHAUFFEUR, positionChauffeur.getDate_heure_chauffeur());
        values.put(KEY_ID_CHAUFFEUR, positionChauffeur.getId_chauffeur());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updatePositionChauffeur(PositionChauffeur positionChauffeur) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_POSITION_CHAUFFEUR, positionChauffeur.getId_position_chauffeur());
        values.put(KEY_LATITUDE_CHAUFFEUR, positionChauffeur.getLatitude_chauffeur());
        values.put(KEY_LONGITUDE_CHAUFFEUR, positionChauffeur.getLongitude_chauffeur());
        values.put(KEY_DATE_HEURE_CHAUFFEUR, positionChauffeur.getDate_heure_chauffeur());
        values.put(KEY_ID_CHAUFFEUR, positionChauffeur.getId_chauffeur());

        String where = KEY_ID_POSITION_CHAUFFEUR+" = ?";
        String[] whereArgs = {positionChauffeur.getId_position_chauffeur()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deletePositionChauffeur(PositionChauffeur positionChauffeur) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_POSITION_CHAUFFEUR+" = ?";
        String[] whereArgs = {positionChauffeur.getId_position_chauffeur()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public PositionChauffeur getPositionChauffeur(Long id) {
        // Retourne le niveau dont l'id est passé en paramètre

        PositionChauffeur p = new PositionChauffeur(0,0,0,"","");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_POSITION_CHAUFFEUR+"="+id, null);
        if (c.moveToFirst()) {
            p.setId_position_chauffeur(c.getLong(c.getColumnIndex(KEY_ID_POSITION_CHAUFFEUR)));
            p.setLatitude_chauffeur(c.getFloat(c.getColumnIndex(KEY_LATITUDE_CHAUFFEUR)));
            p.setLongitude_chauffeur(c.getFloat(c.getColumnIndex(KEY_LONGITUDE_CHAUFFEUR)));
            p.setDate_heure_chauffeur(c.getString(c.getColumnIndex(KEY_DATE_HEURE_CHAUFFEUR)));
            p.setId_chauffeur(c.getString(c.getColumnIndex(KEY_ID_CHAUFFEUR)));

            c.close();
        }

        return p;
    }

    public Cursor getAllPositionChauffeur() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
