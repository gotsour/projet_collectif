package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.object.Chauffeur;
import com.ufrstgi.imr.application.object.Horaire;
import com.ufrstgi.imr.application.object.Personne;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class HoraireManager {

    private static final String TABLE_NAME = "horaire";
    public static final String KEY_ID_HORAIRE = "id_horaire";
    public static final String KEY_HEURE_DEBUT = "heure_debut";
    public static final String KEY_HEURE_FIN = "heure_fin";
    public static final String KEY_ID_CHAUFFEUR = "id_chauffeur";

    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public static final String CREATE_TABLE_HORAIRE =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_HORAIRE+" INTEGER primary key," +
                    " "+KEY_HEURE_DEBUT+" TEXT," +
                    " "+KEY_HEURE_FIN+" TEXT," +
                    " "+KEY_ID_CHAUFFEUR+" TEXT," +
                    " FOREIGN KEY("+KEY_ID_CHAUFFEUR+") REFERENCES chauffeur("+KEY_ID_CHAUFFEUR+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    Context context;

    public HoraireManager(Context context) {
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

    public long addHoraire(Horaire horaire) {
        // Ajout d'un enregistrement dans la table

        String heure_debut = df.format(horaire.getHeure_debut());
        String heure_fin = df.format(horaire.getHeure_fin());

        ContentValues values = new ContentValues();
        values.put(KEY_ID_HORAIRE, horaire.getId_horaire());
        values.put(KEY_HEURE_DEBUT, heure_debut);
        values.put(KEY_HEURE_FIN, heure_fin);
        values.put(KEY_ID_CHAUFFEUR, horaire.getChauffeur().getId_chauffeur());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateHoraire(Horaire horaire) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        String heure_debut = df.format(horaire.getHeure_debut());
        String heure_fin = df.format(horaire.getHeure_fin());

        ContentValues values = new ContentValues();
        values.put(KEY_ID_HORAIRE, horaire.getId_horaire());
        values.put(KEY_HEURE_DEBUT, heure_debut);
        values.put(KEY_HEURE_FIN, heure_fin);
        values.put(KEY_ID_CHAUFFEUR, horaire.getChauffeur().getId_chauffeur());

        String where = KEY_ID_HORAIRE+" = ?";
        String[] whereArgs = {horaire.getId_horaire()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteHoraire(Horaire horaire) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_HORAIRE+" = ?";
        String[] whereArgs = {horaire.getId_horaire()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Horaire getHoraire(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Personne personne = new Personne(0,"","","");
        Chauffeur chauffeur = new Chauffeur("","",0,personne);
        Date heure_debut = new Date();
        Date heure_fin = new Date();

        Horaire h = new Horaire(0,heure_debut,heure_fin,chauffeur);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_HORAIRE+"="+id, null);
        if (c.moveToFirst()) {
            h.setId_horaire(c.getInt(c.getColumnIndex(KEY_ID_HORAIRE)));
            try {
                heure_debut = df.parse(c.getString(c.getColumnIndex(KEY_HEURE_DEBUT)));
                h.setHeure_debut(heure_debut);
                heure_fin = df.parse(c.getString(c.getColumnIndex(KEY_HEURE_FIN)));
                h.setHeure_fin(heure_fin);
            } catch (ParseException pe) {
                System.err.println("Erreur parsage date dans HoraireManager");
            }

            String id_chauffeur = c.getString(c.getColumnIndex(KEY_ID_CHAUFFEUR));
            ChauffeurManager chauffeurManager = new ChauffeurManager(context);
            chauffeurManager.open();
            chauffeur = chauffeurManager.getChauffeur(id_chauffeur);
            chauffeurManager.close();
            h.setChauffeur(chauffeur);

            c.close();
        }

        return h;
    }

    public Cursor getAllHoraire() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
