package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.object.Personne;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class PersonneManager {

    private static final String TABLE_NAME = "personne";
    public static final String KEY_ID_PERSONNE = "id_personne";
    public static final String KEY_NOM_PERSONNE = "nom_personne";
    public static final String KEY_PRENOM_PERSONNE= "prenom_personne";
    public static final String KEY_TELEPHONE_PERSONNE= "telephone_personne";

    public static final String CREATE_TABLE_PERSONNE =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_PERSONNE+" INTEGER primary key," +
                    " "+KEY_NOM_PERSONNE+" TEXT," +
                    " "+KEY_PRENOM_PERSONNE+" TEXT," +
                    " "+KEY_TELEPHONE_PERSONNE+" TEXT" +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public PersonneManager(Context context) {
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

    public long addPersonne(Personne personne) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_PERSONNE, personne.getNom_personne());
        values.put(KEY_PRENOM_PERSONNE, personne.getPrenom_personne());
        values.put(KEY_TELEPHONE_PERSONNE, personne.getTelephone_personne());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updatePersonne(Personne personne) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_PERSONNE, personne.getNom_personne());
        values.put(KEY_PRENOM_PERSONNE, personne.getPrenom_personne());
        values.put(KEY_TELEPHONE_PERSONNE, personne.getTelephone_personne());

        String where = KEY_ID_PERSONNE+" = ?";
        String[] whereArgs = {personne.getId_personne()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deletePersonne(Personne personne) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_PERSONNE+" = ?";
        String[] whereArgs = {personne.getId_personne()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Personne getPersonne(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Personne p = new Personne(0,"","","");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_PERSONNE+"="+id, null);
        if (c.moveToFirst()) {
            p.setId_personne(c.getInt(c.getColumnIndex(KEY_ID_PERSONNE)));
            p.setNom_personne(c.getString(c.getColumnIndex(KEY_NOM_PERSONNE)));
            p.setPrenom_personne(c.getString(c.getColumnIndex(KEY_PRENOM_PERSONNE)));
            p.setTelephone_personne(c.getString(c.getColumnIndex(KEY_TELEPHONE_PERSONNE)));

            c.close();
        }

        return p;
    }

    public Cursor getAllPersonne() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
