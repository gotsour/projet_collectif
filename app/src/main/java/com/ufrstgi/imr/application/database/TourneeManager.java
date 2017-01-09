package com.ufrstgi.imr.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.objet.Tournee;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class TourneeManager {

    private static final String TABLE_NAME = "tournee";
    public static final String KEY_ID_TOURNEE = "id_tournee";
    public static final String KEY_ID_CHAUFFEUR = "id_chauffeur";
    public static final String KEY_ID_CAMION = "id_camion";

    public static final String CREATE_TABLE_TOURNEE =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_TOURNEE+" INTEGER primary key," +
                    " "+KEY_ID_CHAUFFEUR+" TEXT," +
                    " "+KEY_ID_CAMION+" TEXT," +
                    " FOREIGN KEY("+KEY_ID_CHAUFFEUR+") REFERENCES chauffeur("+KEY_ID_CHAUFFEUR+")," +
                    " FOREIGN KEY("+KEY_ID_CAMION+") REFERENCES camion("+KEY_ID_CAMION+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public TourneeManager(Context context) {
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

    public long addTournee(Tournee tournee) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TOURNEE, tournee.getId_tournee());
        values.put(KEY_ID_CHAUFFEUR, tournee.getId_chauffeur());
        values.put(KEY_ID_CAMION, tournee.getId_camion());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateTournee(Tournee tournee) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TOURNEE, tournee.getId_tournee());
        values.put(KEY_ID_CHAUFFEUR, tournee.getId_chauffeur());
        values.put(KEY_ID_CAMION, tournee.getId_camion());

        String where = KEY_ID_TOURNEE+" = ?";
        String[] whereArgs = {tournee.getId_tournee()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteTournee(Tournee tournee) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_TOURNEE+" = ?";
        String[] whereArgs = {tournee.getId_tournee()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Tournee getTournee(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Tournee t = new Tournee(0,"","");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_TOURNEE+"="+id, null);
        if (c.moveToFirst()) {
            t.setId_tournee(c.getInt(c.getColumnIndex(KEY_ID_TOURNEE)));
            t.setId_chauffeur(c.getString(c.getColumnIndex(KEY_ID_CHAUFFEUR)));
            t.setId_camion(c.getString(c.getColumnIndex(KEY_ID_CAMION)));

            c.close();
        }

        return t;
    }

    public Cursor getAllTournee() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
