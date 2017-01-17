package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.object.Chauffeur;
import com.ufrstgi.imr.application.object.Personne;

import java.util.ArrayList;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class ChauffeurManager {

    public static final String TABLE_NAME = "chauffeur";
    public static final String KEY_ID_CHAUFFEUR = "id_chauffeur";
    public static final String KEY_MOT_DE_PASSE = "mot_de_passe";
    public static final String KEY_NIVEAU_BATTERIE_TERMINAL = "niveau_batterie_terminal";
    public static final String KEY_ID_PERSONNE= "id_personne";

    public static final String CREATE_TABLE_CHAUFFEUR =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_CHAUFFEUR+" TEXT primary key," +
                    " "+KEY_MOT_DE_PASSE+" TEXT," +
                    " "+KEY_NIVEAU_BATTERIE_TERMINAL+" REAL," +
                    " "+KEY_ID_PERSONNE+" INTEGER," +
                    " FOREIGN KEY("+KEY_ID_PERSONNE+") REFERENCES personne("+KEY_ID_PERSONNE+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    Context context;

    public ChauffeurManager(Context context) {
        maBaseSQLite = MySQLite.getInstance(context);
        this.context = context;
    }

    public void open() {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addChauffeur(Chauffeur chauffeur) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CHAUFFEUR, chauffeur.getId_chauffeur());
        values.put(KEY_MOT_DE_PASSE, chauffeur.getMot_de_passe());
        values.put(KEY_NIVEAU_BATTERIE_TERMINAL, chauffeur.getNiveau_batterie_terminal());
        values.put(KEY_ID_PERSONNE, chauffeur.getPersonne().getId_personne());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateChauffeur(Chauffeur chauffeur) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CHAUFFEUR, chauffeur.getId_chauffeur());
        values.put(KEY_MOT_DE_PASSE, chauffeur.getMot_de_passe());
        values.put(KEY_NIVEAU_BATTERIE_TERMINAL, chauffeur.getNiveau_batterie_terminal());
        values.put(KEY_ID_PERSONNE, chauffeur.getPersonne().getId_personne());

        String where = KEY_ID_CHAUFFEUR+" = ?";
        String[] whereArgs = {chauffeur.getId_chauffeur()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteChauffeur(Chauffeur chauffeur) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_CHAUFFEUR+" = ?";
        String[] whereArgs = {chauffeur.getId_chauffeur()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Chauffeur getChauffeur(String id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Personne p = new Personne(0,"","","");
        Chauffeur ch = new Chauffeur("","",0,p);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_CHAUFFEUR+"='"+id+"'", null);
        if (c.moveToFirst()) {
            ch.setId_chauffeur(c.getString(c.getColumnIndex(KEY_ID_CHAUFFEUR)));
            ch.setMot_de_passe(c.getString(c.getColumnIndex(KEY_MOT_DE_PASSE)));
            ch.setNiveau_batterie_terminal(c.getFloat(c.getColumnIndex(KEY_NIVEAU_BATTERIE_TERMINAL)));

            int id_personne = c.getInt(c.getColumnIndex(KEY_ID_PERSONNE));
            PersonneManager personneManager = new PersonneManager(context);
            personneManager.open();
            p = personneManager.getPersonne(id_personne);
            personneManager.close();
            ch.setPersonne(p);

            c.close();
        }

        return ch;
    }

    public ArrayList<Chauffeur> getAllChauffeur() {
        ArrayList<Chauffeur> mesChauffeur = new ArrayList<>();
        // sélection de tous les enregistrements de la table
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        Personne p;
        Chauffeur ch;

        if (c.moveToFirst()) {
            do {
                p = new Personne(0,"","","");
                ch = new Chauffeur("","",0,p);

                ch.setId_chauffeur(c.getString(c.getColumnIndex(KEY_ID_CHAUFFEUR)));
                ch.setMot_de_passe(c.getString(c.getColumnIndex(KEY_MOT_DE_PASSE)));
                ch.setNiveau_batterie_terminal(c.getFloat(c.getColumnIndex(KEY_NIVEAU_BATTERIE_TERMINAL)));

                int id_personne = c.getInt(c.getColumnIndex(KEY_ID_PERSONNE));
                PersonneManager personneManager = new PersonneManager(context);
                personneManager.open();
                p = personneManager.getPersonne(id_personne);
                personneManager.close();
                ch.setPersonne(p);

                mesChauffeur.add(ch);
            } while (c.moveToNext());
        }
        c.close();
        return mesChauffeur;
    }
}
