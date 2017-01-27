package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ufrstgi.imr.application.object.Adresse;
import com.ufrstgi.imr.application.object.Client;
import com.ufrstgi.imr.application.object.Latlng;
import com.ufrstgi.imr.application.object.Livraison;
import com.ufrstgi.imr.application.object.Operation;
import com.ufrstgi.imr.application.object.Personne;
import com.ufrstgi.imr.application.object.Reception;

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

public class OperationManager {

    public static final String TABLE_NAME = "operation";
    public static final String KEY_ID_OPERATION = "id_operation";
    public static final String KEY_DATE_THEORIQUE = "date_theorique";
    public static final String KEY_DATE_REELLE= "heure_relle_operation";
    public static final String KEY_DATE_LIMITE= "date_limite_operation";
    public static final String KEY_EST_LIVRAISON= "estLivraison";
    public static final String KEY_QUAI= "quai";
    public static final String KEY_BATIMENT= "batiment";
    public static final String KEY_ID_ADRESSE= "id_adresse";
    public static final String KEY_ID_CLIENT= "id_client";

    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public static final String CREATE_TABLE_OPERATION =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_OPERATION+" INTEGER primary key," +
                    " "+KEY_DATE_THEORIQUE+" TEXT," +
                    " "+KEY_DATE_REELLE+" TEXT," +
                    " "+KEY_DATE_LIMITE+" TEXT," +
                    " "+KEY_EST_LIVRAISON+" INTEGER," +
                    " "+KEY_QUAI+" TEXT," +
                    " "+KEY_BATIMENT+" TEXT," +
                    " "+KEY_ID_ADRESSE+" INTEGER," +
                    " "+KEY_ID_CLIENT+" INTEGER," +
                    " FOREIGN KEY("+KEY_ID_ADRESSE+") REFERENCES adresse("+KEY_ID_ADRESSE+")," +
                    " FOREIGN KEY("+KEY_ID_CLIENT+") REFERENCES client("+KEY_ID_CLIENT+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    Context context;

    public OperationManager(Context context) {
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

    public long addOperation(Operation operation) {
        // Ajout d'un enregistrement dans la table

        String date_theorique = df.format(operation.getDate_theorique());
        String date_reelle;
        if(operation.getDate_reelle() != null)
            date_reelle = df.format(operation.getDate_reelle());
        else
            date_reelle="";
        String date_limite = df.format(operation.getDate_limite());

        ContentValues values = new ContentValues();
        values.put(KEY_ID_OPERATION, operation.getId_operation());
        values.put(KEY_DATE_THEORIQUE, date_theorique);
        values.put(KEY_DATE_REELLE, date_reelle);
        values.put(KEY_DATE_LIMITE, date_limite);
        if (operation instanceof Livraison) {
            values.put(KEY_EST_LIVRAISON, "1");
        } else if (operation instanceof Reception) {
            values.put(KEY_EST_LIVRAISON, "0");
        }
        values.put(KEY_QUAI, operation.getQuai());
        values.put(KEY_BATIMENT, operation.getBatiment());
        values.put(KEY_ID_ADRESSE, operation.getAdresse().getId_adresse());
        values.put(KEY_ID_CLIENT, operation.getClient().getId_client());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateOperation(Operation operation) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        String date_theorique = df.format(operation.getDate_theorique());
        String date_reelle = df.format(operation.getDate_reelle());
        String date_limite = df.format(operation.getDate_limite());

        ContentValues values = new ContentValues();
        values.put(KEY_DATE_THEORIQUE, date_theorique);
        values.put(KEY_DATE_REELLE, date_reelle);
        values.put(KEY_DATE_LIMITE, date_limite);
        if (operation instanceof Livraison) {
            values.put(KEY_EST_LIVRAISON, "1");
        } else if (operation instanceof Reception) {
            values.put(KEY_EST_LIVRAISON, "0");
        }
        values.put(KEY_QUAI, operation.getQuai());
        values.put(KEY_BATIMENT, operation.getBatiment());
        values.put(KEY_ID_ADRESSE, operation.getAdresse().getId_adresse());
        values.put(KEY_ID_CLIENT, operation.getClient().getId_client());

        String where = KEY_ID_OPERATION+" = ?";
        String[] whereArgs = {operation.getId_operation()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteOperation(Operation operation) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_OPERATION+" = ?";
        String[] whereArgs = {operation.getId_operation()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Operation getOperation(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Latlng latlng = new Latlng(0,0,0);
        Adresse adresse = new Adresse(0,"",0,"","",latlng);
        Personne personne = new Personne(0,"","","");
        Client client = new Client(0,"","",adresse,personne);
        Operation o = null;
        Date date_theorique = new Date();
        Date date_reelle = new Date();
        Date date_limite = new Date();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_OPERATION+"="+id, null);
        if (c.moveToFirst()) {
            int estLivraison = c.getInt(c.getColumnIndex(KEY_EST_LIVRAISON));
            if (estLivraison == 1) {
                o = new Livraison(0,date_theorique,date_reelle,date_limite,"","",adresse,client);
            } else {
                o = new Reception(0,date_theorique,date_reelle,date_limite,"","",adresse,client);
            }

            o.setId_operation(c.getInt(c.getColumnIndex(KEY_ID_OPERATION)));
            try {
                date_theorique = df.parse(c.getString(c.getColumnIndex(KEY_DATE_THEORIQUE)));
                if(c.getString(c.getColumnIndex(KEY_DATE_REELLE)).length()>5){
                    date_reelle = df.parse(c.getString(c.getColumnIndex(KEY_DATE_REELLE)));
                }else{
                    date_reelle=null;
                }
                date_limite = df.parse(c.getString(c.getColumnIndex(KEY_DATE_LIMITE)));
            } catch (ParseException pe) {
                System.err.println("Erreur parsage date dans OperationManager");
            }
            o.setDate_theorique(date_theorique);
            o.setDate_reelle(date_reelle);
            o.setDate_limite(date_limite);
            o.setQuai(c.getString(c.getColumnIndex(KEY_QUAI)));
            o.setBatiment(c.getString(c.getColumnIndex(KEY_BATIMENT)));

            int id_adresse = c.getInt(c.getColumnIndex(KEY_ID_ADRESSE));
            AdresseManager adresseManager = new AdresseManager(context);
            adresseManager.open();
            adresse = adresseManager.getAdresse(id_adresse);
            adresseManager.close();
            o.setAdresse(adresse);

            int id_client = c.getInt(c.getColumnIndex(KEY_ID_CLIENT));
            ClientManager clientManager = new ClientManager(context);
            clientManager.open();
            client = clientManager.getClient(id_client);
            clientManager.close();
            o.setClient(client);

            c.close();
        }

        return o;
    }

    public ArrayList<Operation> getAllOperation() {
        ArrayList<Operation> mesOperation = new ArrayList<>();
        // sélection de tous les enregistrements de la table
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME +" ORDER BY Date(substr(date_theorique, 7, 4) || '-' || substr(date_theorique, 1, 2) || '-' || substr(date_theorique, 4, 2) || substr(date_theorique, 11, 9)) ASC", null);
        Latlng latlng;
        Adresse adresse;
        Personne personne;
        Client client;
        Operation o;
        Date date_theorique;
        Date date_reelle;
        Date date_limite;

        if (c.moveToFirst()) {
            do {
                latlng = new Latlng(0,0,0);
                adresse = new Adresse(0,"",0,"","",latlng);
                personne = new Personne(0,"","","");
                client = new Client(0,"","",adresse,personne);
                o = null;
                date_theorique = new Date();
                date_reelle = new Date();
                date_limite = new Date();

                int estLivraison = c.getInt(c.getColumnIndex(KEY_EST_LIVRAISON));
                if (estLivraison == 1) {
                    o = new Livraison(0,date_theorique,date_reelle,date_limite,"","",adresse,client);
                } else {
                    o = new Reception(0,date_theorique,date_reelle,date_limite,"","",adresse,client);
                }

                o.setId_operation(c.getInt(c.getColumnIndex(KEY_ID_OPERATION)));
                try {
                    date_theorique = df.parse(c.getString(c.getColumnIndex(KEY_DATE_THEORIQUE)));
                    if(c.getString(c.getColumnIndex(KEY_DATE_REELLE)).length()>5){
                        date_reelle = df.parse(c.getString(c.getColumnIndex(KEY_DATE_REELLE)));
                    }else{
                        date_reelle=null;
                    }
                    date_limite = df.parse(c.getString(c.getColumnIndex(KEY_DATE_LIMITE)));
                } catch (ParseException pe) {
                    System.err.println("Erreur parsage date dans OperationManager");
                }
                o.setDate_theorique(date_theorique);
                o.setDate_reelle(date_reelle);
                o.setDate_limite(date_limite);
                o.setQuai(c.getString(c.getColumnIndex(KEY_QUAI)));
                o.setBatiment(c.getString(c.getColumnIndex(KEY_BATIMENT)));

                int id_adresse = c.getInt(c.getColumnIndex(KEY_ID_ADRESSE));
                AdresseManager adresseManager = new AdresseManager(context);
                adresseManager.open();
                adresse = adresseManager.getAdresse(id_adresse);
                adresseManager.close();
                o.setAdresse(adresse);

                int id_client = c.getInt(c.getColumnIndex(KEY_ID_CLIENT));
                ClientManager clientManager = new ClientManager(context);
                clientManager.open();
                client = clientManager.getClient(id_client);
                clientManager.close();
                o.setClient(client);

                mesOperation.add(o);
            } while (c.moveToNext());
        }
        c.close();
        return mesOperation;
    }

    public long addAllOfOperation(Operation operation) {
        ClientManager clientManager = new ClientManager(context);
        clientManager.open();
        clientManager.addAllOfClient(operation.getClient());
        clientManager.close();

        AdresseManager adresseManager = new AdresseManager(context);
        adresseManager.open();
        adresseManager.addAllOfAdresse(operation.getAdresse());
        adresseManager.close();

        String date_theorique = df.format(operation.getDate_theorique());
        String date_reelle;
        if(operation.getDate_reelle() != null)
            date_reelle = df.format(operation.getDate_reelle());
        else
            date_reelle="";
        String date_limite = df.format(operation.getDate_limite());

        ContentValues values = new ContentValues();
        values.put(KEY_ID_OPERATION, operation.getId_operation());
        values.put(KEY_DATE_THEORIQUE, date_theorique);
        values.put(KEY_DATE_REELLE, date_reelle);
        values.put(KEY_DATE_LIMITE, date_limite);
        if (operation instanceof Livraison) {
            values.put(KEY_EST_LIVRAISON, "1");
        } else if (operation instanceof Reception) {
            values.put(KEY_EST_LIVRAISON, "0");
        }
        values.put(KEY_QUAI, operation.getQuai());
        values.put(KEY_BATIMENT, operation.getBatiment());
        values.put(KEY_ID_ADRESSE, operation.getAdresse().getId_adresse());
        values.put(KEY_ID_CLIENT, operation.getClient().getId_client());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        this.open();
        return db.insert(TABLE_NAME,null,values);

    }

    public long updateAllOfOperation(Operation operation) {
        ClientManager clientManager = new ClientManager(context);
        clientManager.open();
        clientManager.updateAllOfClient(operation.getClient());
        clientManager.close();

        AdresseManager adresseManager = new AdresseManager(context);
        adresseManager.open();
        adresseManager.updateAllOfAdresse(operation.getAdresse());
        adresseManager.close();

        String date_theorique = df.format(operation.getDate_theorique());
        String date_limite = df.format(operation.getDate_limite());

        ContentValues values = new ContentValues();
        values.put(KEY_DATE_THEORIQUE, date_theorique);
        values.put(KEY_DATE_LIMITE, date_limite);
        if (operation instanceof Livraison) {
            values.put(KEY_EST_LIVRAISON, "1");
        } else if (operation instanceof Reception) {
            values.put(KEY_EST_LIVRAISON, "0");
        }
        values.put(KEY_QUAI, operation.getQuai());
        values.put(KEY_BATIMENT, operation.getBatiment());
        values.put(KEY_ID_ADRESSE, operation.getAdresse().getId_adresse());
        values.put(KEY_ID_CLIENT, operation.getClient().getId_client());


        this.open();
        String where = KEY_ID_OPERATION+" = ?";
        String[] whereArgs = {operation.getId_operation()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }
}
