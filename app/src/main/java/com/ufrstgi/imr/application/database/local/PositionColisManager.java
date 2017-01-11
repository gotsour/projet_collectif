package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.object.Adresse;
import com.ufrstgi.imr.application.object.Camion;
import com.ufrstgi.imr.application.object.Chauffeur;
import com.ufrstgi.imr.application.object.Client;
import com.ufrstgi.imr.application.object.Colis;
import com.ufrstgi.imr.application.object.Latlng;
import com.ufrstgi.imr.application.object.Niveau;
import com.ufrstgi.imr.application.object.Operation;
import com.ufrstgi.imr.application.object.Personne;
import com.ufrstgi.imr.application.object.PositionColis;
import com.ufrstgi.imr.application.object.Tournee;

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

public class PositionColisManager {

    private static final String TABLE_NAME = "positionColis";
    public static final String KEY_ID_POSITION_COLIS = "id_position_colis";
    public static final String KEY_DATE_HEURE_COLIS = "date_heure_colis";
    public static final String KEY_ID_COLIS = "id_colis";
    public static final String KEY_ID_LATLNG = "id_latlng";

    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public static final String CREATE_TABLE_POSITION_COLIS =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_POSITION_COLIS+" INTEGER primary key," +
                    " "+KEY_DATE_HEURE_COLIS+" TEXT," +
                    " "+KEY_ID_COLIS+" INTEGER," +
                    " "+KEY_ID_LATLNG+" INTEGER," +
                    " FOREIGN KEY("+KEY_ID_COLIS+") REFERENCES colis("+KEY_ID_COLIS+")," +
                    " FOREIGN KEY("+KEY_ID_LATLNG+") REFERENCES latlng("+KEY_ID_LATLNG+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    Context context;

    public PositionColisManager(Context context) {
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

    public long addPositionColis(PositionColis positionColis) {
        // Ajout d'un enregistrement dans la table

        String date_heure_colis = df.format(positionColis.getDate_heure_colis());

        ContentValues values = new ContentValues();
        values.put(KEY_ID_POSITION_COLIS, positionColis.getId_position_colis());
        values.put(KEY_DATE_HEURE_COLIS, date_heure_colis);
        values.put(KEY_ID_COLIS, positionColis.getColis().getId_colis());
        values.put(KEY_ID_LATLNG, positionColis.getLatlng().getId_latlng());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updatePositionColis(PositionColis positionColis) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        String date_heure_colis = df.format(positionColis.getDate_heure_colis());

        ContentValues values = new ContentValues();
        values.put(KEY_ID_POSITION_COLIS, positionColis.getId_position_colis());
        values.put(KEY_DATE_HEURE_COLIS, date_heure_colis);
        values.put(KEY_ID_COLIS, positionColis.getColis().getId_colis());
        values.put(KEY_ID_LATLNG, positionColis.getLatlng().getId_latlng());

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

    public PositionColis getPositionColis(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Niveau niveau = new Niveau(0,"",0);
        Latlng latlng = new Latlng(0,0,0);
        Adresse adresse = new Adresse(0,"",0,"","",latlng);
        Personne personne = new Personne(0,"","","");
        Client client = new Client(0,"","",adresse,personne);
        Operation operation = null;
        Chauffeur chauffeur = new Chauffeur("","",0,personne);
        Camion camion = new Camion("","",0,0,0);
        Tournee tournee = new Tournee(0,chauffeur,camion);
        Colis colis = new Colis(0,0,0,0,0,0,niveau,operation,tournee,client);
        Date date_heure_colis = new Date();
        PositionColis p = new PositionColis(0,date_heure_colis,colis,latlng);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_POSITION_COLIS+"="+id, null);
        if (c.moveToFirst()) {
            p.setId_position_colis(c.getInt(c.getColumnIndex(KEY_ID_POSITION_COLIS)));
            try {
                date_heure_colis = df.parse(c.getString(c.getColumnIndex(KEY_DATE_HEURE_COLIS)));
            } catch (ParseException pe) {
                System.err.println("Erreur parsage date dans PositionColisManager");
            }
            p.setDate_heure_colis(date_heure_colis);

            int id_colis = c.getInt(c.getColumnIndex(KEY_ID_COLIS));
            ColisManager colisManager = new ColisManager(context);
            colisManager.open();
            colis = colisManager.getColis(id_colis);
            colisManager.close();
            p.setColis(colis);

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

    public ArrayList<PositionColis> getAllPositionColis() {
        ArrayList<PositionColis> mesPositionColis = new ArrayList<>();
        // sélection de tous les enregistrements de la table
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        Niveau niveau;
        Latlng latlng;
        Adresse adresse;
        Personne personne;
        Client client;
        Operation operation;
        Chauffeur chauffeur;
        Camion camion;
        Tournee tournee;
        Colis colis;
        Date date_heure_colis;
        PositionColis p;

        if (c.moveToFirst()) {
            do {
                niveau = new Niveau(0,"",0);
                latlng = new Latlng(0,0,0);
                adresse = new Adresse(0,"",0,"","",latlng);
                personne = new Personne(0,"","","");
                client = new Client(0,"","",adresse,personne);
                operation = null;
                chauffeur = new Chauffeur("","",0,personne);
                camion = new Camion("","",0,0,0);
                tournee = new Tournee(0,chauffeur,camion);
                colis = new Colis(0,0,0,0,0,0,niveau,operation,tournee,client);
                date_heure_colis = new Date();
                p = new PositionColis(0,date_heure_colis,colis,latlng);

                p.setId_position_colis(c.getInt(c.getColumnIndex(KEY_ID_POSITION_COLIS)));
                try {
                    date_heure_colis = df.parse(c.getString(c.getColumnIndex(KEY_DATE_HEURE_COLIS)));
                } catch (ParseException pe) {
                    System.err.println("Erreur parsage date dans PositionColisManager");
                }
                p.setDate_heure_colis(date_heure_colis);

                int id_colis = c.getInt(c.getColumnIndex(KEY_ID_COLIS));
                ColisManager colisManager = new ColisManager(context);
                colisManager.open();
                colis = colisManager.getColis(id_colis);
                colisManager.close();
                p.setColis(colis);

                int id_latlng = c.getInt(c.getColumnIndex(KEY_ID_LATLNG));
                LatlngManager latlngManager = new LatlngManager(context);
                latlngManager.open();
                latlng = latlngManager.getLatlng(id_latlng);
                latlngManager.close();
                p.setLatlng(latlng);

                mesPositionColis.add(p);
            } while (c.moveToNext());
        }

        c.close();
        return mesPositionColis;
    }
}
