package com.ufrstgi.imr.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.objet.Camion;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class CamionManager {

    private static final String TABLE_NAME = "camion";
    public static final String KEY_ID_CAMION = "id_camion";
    public static final String KEY_NOM_CAMION = "nom_camion";
    public static final String KEY_VOLUME_CAMION= "volume_camion";
    public static final String KEY_TAILLE_CAMION= "taille_camion";
    public static final String KEY_POIDS_CHARGEMENT_CAMION = "poids_chargement_camion";

    public static final String CREATE_TABLE_CAMION =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_CAMION+" TEXT primary key," +
                    " "+KEY_NOM_CAMION+" TEXT," +
                    " "+KEY_VOLUME_CAMION+" REAL," +
                    " "+KEY_TAILLE_CAMION+" REAL," +
                    " "+KEY_POIDS_CHARGEMENT_CAMION+" REAL" +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public CamionManager(Context context) {
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

    public long addCamion(Camion camion) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_CAMION, camion.getNom_camion());
        values.put(KEY_VOLUME_CAMION, camion.getVolume_camion());
        values.put(KEY_TAILLE_CAMION, camion.getTaille_camion());
        values.put(KEY_POIDS_CHARGEMENT_CAMION, camion.getPoids_chargement_camion());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateCamion(Camion camion) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_CAMION, camion.getNom_camion());
        values.put(KEY_VOLUME_CAMION, camion.getVolume_camion());
        values.put(KEY_TAILLE_CAMION, camion.getTaille_camion());
        values.put(KEY_POIDS_CHARGEMENT_CAMION, camion.getPoids_chargement_camion());

        String where = KEY_ID_CAMION+" = ?";
        String[] whereArgs = {camion.getId_camion()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteCamion(Camion camion) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_CAMION+" = ?";
        String[] whereArgs = {camion.getId_camion()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Camion getCamion(String id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Camion cam = new Camion("","",0,0,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_CAMION+"="+id, null);
        if (c.moveToFirst()) {
            cam.setId_camion(c.getString(c.getColumnIndex(KEY_ID_CAMION)));
            cam.setNom_camion(c.getString(c.getColumnIndex(KEY_NOM_CAMION)));
            cam.setVolume_camion(c.getFloat(c.getColumnIndex(KEY_VOLUME_CAMION)));
            cam.setTaille_camion(c.getFloat(c.getColumnIndex(KEY_TAILLE_CAMION)));
            cam.setPoids_chargement_camion(c.getFloat(c.getColumnIndex(KEY_POIDS_CHARGEMENT_CAMION)));

            c.close();
        }

        return cam;
    }

    public Cursor getAllCamion() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

}
