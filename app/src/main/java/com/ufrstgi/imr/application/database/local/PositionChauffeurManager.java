package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.object.Chauffeur;
import com.ufrstgi.imr.application.object.Latlng;
import com.ufrstgi.imr.application.object.Personne;
import com.ufrstgi.imr.application.object.PositionChauffeur;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class PositionChauffeurManager {

    public static final String TABLE_NAME = "positionChauffeur";
    public static final String KEY_ID_POSITION_CHAUFFEUR = "id_position_chauffeur";
    public static final String KEY_DATE_HEURE_CHAUFFEUR= "date_heure_chauffeur";
    public static final String KEY_ID_CHAUFFEUR= "id_chauffeur";
    public static final String KEY_ID_LATLNG= "id_latlng";

    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public static final String CREATE_TABLE_POSITION_CHAUFFEUR =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_POSITION_CHAUFFEUR+" INTEGER primary key," +
                    " "+KEY_DATE_HEURE_CHAUFFEUR+" TEXT," +
                    " "+KEY_ID_CHAUFFEUR+" TEXT," +
                    " "+KEY_ID_LATLNG+" TEXT," +
                    " FOREIGN KEY("+KEY_ID_CHAUFFEUR+") REFERENCES chauffeur("+KEY_ID_CHAUFFEUR+")," +
                    " FOREIGN KEY("+KEY_ID_LATLNG+") REFERENCES latlng("+KEY_ID_LATLNG+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    Context context;

    public PositionChauffeurManager(Context context) {
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

    public long addPositionChauffeur(PositionChauffeur positionChauffeur) {
        // Ajout d'un enregistrement dans la table

        String date_heure_chauffeur = df.format(positionChauffeur.getDate_heure_chauffeur());

        ContentValues values = new ContentValues();
        values.put(KEY_ID_POSITION_CHAUFFEUR, positionChauffeur.getId_position_chauffeur());
        values.put(KEY_DATE_HEURE_CHAUFFEUR, date_heure_chauffeur);
        values.put(KEY_ID_CHAUFFEUR, positionChauffeur.getChauffeur().getId_chauffeur());
        values.put(KEY_ID_LATLNG, positionChauffeur.getLatlng().getId_latlng());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updatePositionChauffeur(PositionChauffeur positionChauffeur) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        String date_heure_chauffeur = df.format(positionChauffeur.getDate_heure_chauffeur());

        ContentValues values = new ContentValues();
        values.put(KEY_ID_POSITION_CHAUFFEUR, positionChauffeur.getId_position_chauffeur());
        values.put(KEY_DATE_HEURE_CHAUFFEUR, date_heure_chauffeur);
        values.put(KEY_ID_CHAUFFEUR, positionChauffeur.getChauffeur().getId_chauffeur());
        values.put(KEY_ID_LATLNG, positionChauffeur.getLatlng().getId_latlng());

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

    public PositionChauffeur getPositionChauffeur(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Personne personne = new Personne(0,"","","");
        Chauffeur chauffeur = new Chauffeur("","",0,personne);
        Latlng latlng = new Latlng(0,0,0);
        Date date_heure_chauffeur = new Date();
        PositionChauffeur p = new PositionChauffeur(0,date_heure_chauffeur,chauffeur,latlng);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_POSITION_CHAUFFEUR+"="+id, null);
        if (c.moveToFirst()) {
            p.setId_position_chauffeur(c.getInt(c.getColumnIndex(KEY_ID_POSITION_CHAUFFEUR)));
            try {
                date_heure_chauffeur = df.parse(c.getString(c.getColumnIndex(KEY_DATE_HEURE_CHAUFFEUR)));
            } catch (ParseException pe) {
                System.err.println("Erreur parsage date dans PositionChauffeurManager");
            }
            p.setDate_heure_chauffeur(date_heure_chauffeur);

            String id_chauffeur = c.getString(c.getColumnIndex(KEY_ID_CHAUFFEUR));
            ChauffeurManager chauffeurManager = new ChauffeurManager(context);
            chauffeurManager.open();
            chauffeur = chauffeurManager.getChauffeur(id_chauffeur);
            chauffeurManager.close();
            p.setChauffeur(chauffeur);

            int id_latlng = c.getInt(c.getColumnIndex(KEY_ID_LATLNG));
            LatlngManager latlngManager = new LatlngManager(context);
            latlngManager.open();
            latlng = latlngManager.getLatlng(id_latlng);
            latlngManager.close();
            p.setLatlng(latlng);

            c.close();
        }

        return p;
    }

    public ArrayList<PositionChauffeur> getAllPositionChauffeur() {
        ArrayList<PositionChauffeur> mesPositionChauffeur = new ArrayList<>();
        // sélection de tous les enregistrements de la table
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        Personne personne;
        Chauffeur chauffeur;
        Latlng latlng;
        Date date_heure_chauffeur;
        PositionChauffeur p;

        if (c.moveToFirst()) {
            do {
                personne = new Personne(0,"","","");
                chauffeur = new Chauffeur("","",0,personne);
                latlng = new Latlng(0,0,0);
                date_heure_chauffeur = new Date();
                p = new PositionChauffeur(0,date_heure_chauffeur,chauffeur,latlng);

                p.setId_position_chauffeur(c.getInt(c.getColumnIndex(KEY_ID_POSITION_CHAUFFEUR)));
                try {
                    date_heure_chauffeur = df.parse(c.getString(c.getColumnIndex(KEY_DATE_HEURE_CHAUFFEUR)));
                } catch (ParseException pe) {
                    System.err.println("Erreur parsage date dans PositionChauffeurManager");
                }
                p.setDate_heure_chauffeur(date_heure_chauffeur);

                String id_chauffeur = c.getString(c.getColumnIndex(KEY_ID_CHAUFFEUR));
                ChauffeurManager chauffeurManager = new ChauffeurManager(context);
                chauffeurManager.open();
                chauffeur = chauffeurManager.getChauffeur(id_chauffeur);
                chauffeurManager.close();
                p.setChauffeur(chauffeur);

                int id_latlng = c.getInt(c.getColumnIndex(KEY_ID_LATLNG));
                LatlngManager latlngManager = new LatlngManager(context);
                latlngManager.open();
                latlng = latlngManager.getLatlng(id_latlng);
                latlngManager.close();
                p.setLatlng(latlng);

                mesPositionChauffeur.add(p);
            } while (c.moveToNext());
        }
        c.close();
        return mesPositionChauffeur;
    }
}
