package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ufrstgi.imr.application.object.Camion;
import com.ufrstgi.imr.application.object.Chauffeur;
import com.ufrstgi.imr.application.object.Personne;
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

public class TourneeManager {

    public static final String TABLE_NAME = "tournee";
    public static final String KEY_DATE_DEBUT = "date_debut";
    public static final String KEY_ID_TOURNEE = "id_tournee";
    public static final String KEY_ID_CHAUFFEUR = "id_chauffeur";
    public static final String KEY_ID_CAMION = "id_camion";

    public static final String CREATE_TABLE_TOURNEE =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_TOURNEE+" INTEGER primary key," +
                    " "+KEY_DATE_DEBUT+" TEXT," +
                    " "+KEY_ID_CHAUFFEUR+" TEXT," +
                    " "+KEY_ID_CAMION+" TEXT," +
                    " FOREIGN KEY("+KEY_ID_CHAUFFEUR+") REFERENCES chauffeur("+KEY_ID_CHAUFFEUR+")," +
                    " FOREIGN KEY("+KEY_ID_CAMION+") REFERENCES camion("+KEY_ID_CAMION+") " +
                    ");";

    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    Context context;

    public TourneeManager(Context context) {
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

    public long addTournee(Tournee tournee) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TOURNEE, tournee.getId_tournee());
        String date_debut = df.format(tournee.getDate_debut());
        values.put(KEY_DATE_DEBUT, date_debut);
        values.put(KEY_ID_CHAUFFEUR, tournee.getChauffeur().getId_chauffeur());
        values.put(KEY_ID_CAMION, tournee.getCamion().getId_camion());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateTournee(Tournee tournee) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TOURNEE, tournee.getId_tournee());
        String date_debut = df.format(tournee.getDate_debut());
        values.put(KEY_DATE_DEBUT, date_debut);
        values.put(KEY_ID_CHAUFFEUR, tournee.getChauffeur().getId_chauffeur());
        values.put(KEY_ID_CAMION, tournee.getCamion().getId_camion());

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

    public Tournee getTournee() {
        // Retourne le niveau dont l'id est passé en paramètre

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME  , null); //" ORDER BY Date(substr(date_debut, 7, 4) || '-' || substr(date_debut, 1, 2) || '-' || substr(date_debut, 4, 2) || substr(date_debut, 11, 9)) DESC Limit 1 ", null);
        if (c.moveToFirst() && c.getCount()>0) {
            int idTournee =c.getInt(c.getColumnIndex(KEY_ID_TOURNEE));
            Date date_debut=new Date();
            try {
                date_debut = df.parse(c.getString(c.getColumnIndex(KEY_DATE_DEBUT)));
            } catch (ParseException pe) {
                System.err.println("Erreur parsage date dans OperationManager");
            }

            Chauffeur chauffeur;
            String id_chauffeur = c.getString(c.getColumnIndex(KEY_ID_CHAUFFEUR));
            ChauffeurManager chauffeurManager= new ChauffeurManager(context);
            chauffeurManager.open();
            chauffeur = chauffeurManager.getChauffeur(id_chauffeur);
            chauffeurManager.close();

            Camion camion ;
            String id_camion = c.getString(c.getColumnIndex(KEY_ID_CAMION));
            CamionManager camionManager= new CamionManager(context);
            camionManager.open();
            camion = camionManager.getCamion(id_camion);
            camionManager.close();


            c.close();
            return new Tournee(idTournee,date_debut,chauffeur,camion);
        }

        return null;
    }

    public Tournee getTournee(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Personne personne = new Personne(0,"","","");
        Chauffeur chauffeur = new Chauffeur("","",0,personne);
        Camion camion = new Camion("","",0,0,0);
        Tournee t = new Tournee(0,null,chauffeur,camion);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_TOURNEE+"="+id, null);
        if (c.moveToFirst()) {
            t.setId_tournee(c.getInt(c.getColumnIndex(KEY_ID_TOURNEE)));
            Date date_debut=new Date();
            try {
                date_debut = df.parse(c.getString(c.getColumnIndex(KEY_DATE_DEBUT)));
            } catch (ParseException pe) {
                System.err.println("Erreur parsage date dans OperationManager");
            }
            t.setDate_debut(date_debut);
            String id_chauffeur = c.getString(c.getColumnIndex(KEY_ID_CHAUFFEUR));
            ChauffeurManager chauffeurManager= new ChauffeurManager(context);
            chauffeurManager.open();
            chauffeur = chauffeurManager.getChauffeur(id_chauffeur);
            chauffeurManager.close();
            t.setChauffeur(chauffeur);

            String id_camion = c.getString(c.getColumnIndex(KEY_ID_CAMION));
            CamionManager camionManager= new CamionManager(context);
            camionManager.open();
            camion = camionManager.getCamion(id_camion);
            camionManager.close();
            t.setCamion(camion);

            c.close();
        }

        return t;
    }

    public ArrayList<Tournee> getAllTournee() {
        ArrayList<Tournee> mesTournee = new ArrayList<>();
        // sélection de tous les enregistrements de la table
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        Personne personne;
        Chauffeur chauffeur;
        Camion camion;
        Tournee t;

        if (c.moveToFirst()) {
            do {
                personne = new Personne(0,"","","");
                chauffeur = new Chauffeur("","",0,personne);
                camion = new Camion("","",0,0,0);
                t = new Tournee(0,null,chauffeur,camion);

                Date date_debut=new Date();
                try {
                    date_debut = df.parse(c.getString(c.getColumnIndex(KEY_DATE_DEBUT)));
                } catch (ParseException pe) {
                    System.err.println("Erreur parsage date dans OperationManager");
                }
                t.setDate_debut(date_debut);

                t.setId_tournee(c.getInt(c.getColumnIndex(KEY_ID_TOURNEE)));

                String id_chauffeur = c.getString(c.getColumnIndex(KEY_ID_CHAUFFEUR));
                ChauffeurManager chauffeurManager= new ChauffeurManager(context);
                chauffeurManager.open();
                chauffeur = chauffeurManager.getChauffeur(id_chauffeur);
                chauffeurManager.close();
                t.setChauffeur(chauffeur);

                String id_camion = c.getString(c.getColumnIndex(KEY_ID_CAMION));
                CamionManager camionManager= new CamionManager(context);
                camionManager.open();
                camion = camionManager.getCamion(id_camion);
                camionManager.close();
                t.setCamion(camion);

                mesTournee.add(t);
            }while (c.moveToNext());
        }
        c.close();
        return mesTournee;
    }

    public long addAllOfTournee(Tournee tournee) {

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TOURNEE, tournee.getId_tournee());

        String date_debut = df.format(tournee.getDate_debut());
        values.put(KEY_DATE_DEBUT, date_debut);

        ChauffeurManager chauffeurManager= new ChauffeurManager(context);
        chauffeurManager.open();
        chauffeurManager.addAllOfChauffeur(tournee.getChauffeur());
        chauffeurManager.close();

        CamionManager camionManager = new CamionManager(context);
        camionManager.open();
        camionManager.addCamion(tournee.getCamion());
        camionManager.close();

        values.put(KEY_ID_CHAUFFEUR, tournee.getChauffeur().getId_chauffeur());
        values.put(KEY_ID_CAMION, tournee.getCamion().getId_camion());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        this.open();
        return db.insert(TABLE_NAME,null,values);
    }

    public long updateAllOfTournee(Tournee tournee) {
        ContentValues values = new ContentValues();

        String date_debut = df.format(tournee.getDate_debut());
        values.put(KEY_DATE_DEBUT, date_debut);

        ChauffeurManager chauffeurManager= new ChauffeurManager(context);
        chauffeurManager.open();
        chauffeurManager.updateAllOfChauffeur(tournee.getChauffeur());
        chauffeurManager.close();

        CamionManager camionManager = new CamionManager(context);
        camionManager.open();
        camionManager.updateCamion(tournee.getCamion());
        camionManager.close();

        values.put(KEY_ID_CHAUFFEUR, tournee.getChauffeur().getId_chauffeur());
        values.put(KEY_ID_CAMION, tournee.getCamion().getId_camion());

        this.open();
        String where = KEY_ID_TOURNEE+" = ?";
        String[] whereArgs = {tournee.getId_tournee()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }
}
